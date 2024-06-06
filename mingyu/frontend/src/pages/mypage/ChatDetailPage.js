import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import ManIcon from "../../img/man_icon.png";
import LeftArrow from "../../img/left-arrow.png";

const ChatDetailPage = () => {
	const { chatId } = useParams();
	const navigate = useNavigate();
	const [chats, setChats] = useState([]);
	const [message, setMessage] = useState("");
	const userId = sessionStorage.getItem("userId");
	const [stompClient, setStompClient] = useState(null);

	const formatTime = (timeString) => {
		const date = new Date(timeString);
		const hours = date.getHours();
		const minutes = date.getMinutes();
		const ampm = hours >= 12 ? "오후" : "오전";
		const formattedHours = hours === 0 ? 12 : hours > 12 ? hours - 12 : hours;
		const formattedMinutes = minutes < 10 ? `0${minutes}` : minutes;
		return `${ampm} ${formattedHours}:${formattedMinutes}`;
	};

	useEffect(() => {
		const fetchChats = async () => {
			try {
				const response = await axios.get(
					`http://localhost:8080/api/chats/${userId}`
				);
				setChats(response.data);
			} catch (error) {
				console.log("error message: ", error);
			}
		};

		fetchChats();

		const socket = new SockJS("http://localhost:8080/ws");
		const stompClientInstance = Stomp.over(socket);
		stompClientInstance.connect({}, () => {
			setStompClient(stompClientInstance);
			const subscription = stompClientInstance.subscribe(
				`/user/${userId}/queue/messages`,
				(message) => {
					const newMessage = JSON.parse(message.body);
					setChats((prevChats) =>
						prevChats.map((chat) => {
							if (chat.room_id === parseInt(chatId)) {
								return {
									...chat,
									messages: [...chat.messages, newMessage],
								};
							}
							return chat;
						})
					);
				}
			);

			return () => {
				subscription.unsubscribe();
				stompClientInstance.disconnect();
			};
		});

		return () => {
			if (stompClient) {
				stompClient.disconnect();
			}
		};
	}, [userId, chatId]);

	const chat = chats.find((chat) => chat.room_id === parseInt(chatId));

	const handleGoBack = () => {
		navigate("/mypage");
	};

	const handleToProfile = (e) => {
		e.preventDefault();
		const state = {
			userId: chat.receiver_id,
		};
		navigate("/otherpage", { state });
	};

	const handleSendMessage = () => {
		if (stompClient && message.trim() !== "") {
			const newMessage = {
				sender: "me",
				content: message,
				time: new Date().toISOString(),
			};

			stompClient.send(
				"/app/chat.sendMessage",
				{},
				JSON.stringify({
					senderId: userId,
					receiverId: chat.receiver_id,
					message: message,
				})
			);

			setChats((prevChats) => {
				const updatedChats = prevChats.map((chat) => {
					if (chat.room_id === parseInt(chatId)) {
						return {
							...chat,
							messages: [...chat.messages, newMessage],
						};
					}
					return chat;
				});
				return updatedChats;
			});

			setMessage("");
		}
	};

	if (!chat) {
		return <div>채팅 내용이 없습니다.</div>;
	}

	return (
		<div className="flex flex-col h-screen">
			<header className="bg-blue-500 text-white py-4 px-6 flex items-center">
				<button className="w-8 h-8 font-bold mr-5" onClick={handleGoBack}>
					<img src={LeftArrow} alt="Back" />
				</button>
				<button
					onClick={handleToProfile}
					className="flex items-center justify-center"
				>
					<img
						src={`data:image/jpeg;base64,${chat.receiver_profileImage}`}
						alt={chat.receiver_name}
						className="w-10 h-10 rounded-full mr-4"
					/>
					<h1 className="text-xl font-bold">{chat.receiver_name}</h1>
				</button>
			</header>
			<main className="flex-1 overflow-y-auto bg-gray-100 p-4">
				{chat.messages
					.slice()
					.sort((a, b) => new Date(a.time) - new Date(b.time))
					.map((message, index) => (
						<div
							key={index}
							className={`py-2 ${
								message.sender === "me" ? "text-right" : "text-left"
							}`}
						>
							<div
								className={`inline-block px-4 py-2 rounded-lg ${
									message.sender === "me"
										? "bg-blue-500 text-white"
										: "bg-gray-300 text-gray-800"
								}`}
							>
								{message.content}
							</div>
							<span className="text-xs text-gray-500 ml-2">
								{formatTime(message.time)}
							</span>
						</div>
					))}
			</main>
			<footer className="bg-white py-4 px-6 flex">
				<input
					type="text"
					value={message}
					onChange={(e) => setMessage(e.target.value)}
					placeholder="메시지를 입력하세요"
					className="flex-1 px-4 py-2 rounded-lg border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
				/>
				<button
					onClick={handleSendMessage}
					className="ml-4 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500"
				>
					전송
				</button>
			</footer>
		</div>
	);
};

export default ChatDetailPage;
