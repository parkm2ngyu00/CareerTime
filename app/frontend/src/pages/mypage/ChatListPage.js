import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ManIcon from "../../img/man_icon.png";
import LeftArrow from "../../img/left-arrow.png";
import axios from "axios";

const ChatListPage = () => {
	const navigate = useNavigate();
	const userId = sessionStorage.getItem("userId");
	const [chats, setChats] = useState([]);

	const handleChatClick = (chatId) => {
		navigate(`/chat/${chatId}`);
	};

	useEffect(() => {
		const fetchChats = async () => {
			try {
				const respone = await axios.get(
					`http://localhost:8080/api/chats/${userId}`
				);
				setChats(respone.data);
				console.log(respone.data);
			} catch (error) {
				console.log("error message: ", error);
			}
		};

		fetchChats();
	}, []);

	return (
		<div className="flex flex-col">
			<header className="bg-blue-500 text-white py-4 px-6">
				<h1 className="text-xl font-bold">채팅 목록</h1>
			</header>
			<main className="flex-1 overflow-y-auto bg-gray-100 p-4">
				{chats.map((chat) => (
					<div
						key={chat.room_id}
						className="bg-white rounded-lg shadow-md mb-4 p-4 flex items-center cursor-pointer"
						onClick={() => handleChatClick(chat.room_id)}
					>
						<img
							src={`data:image/jpeg;base64,${chat.receiver_profileImage}`}
							alt={chat.name}
							className="w-12 h-12 rounded-full mr-4"
						/>
						<div className="flex-1">
							<h2 className="text-lg font-bold">{chat.receiver_name}</h2>
							<p className="text-gray-600">{chat.lastChatMessage}</p>
							<p className="text-gray-500 text-sm">{chat.lastChatDate}</p>
						</div>
					</div>
				))}
			</main>
		</div>
	);
};

export default ChatListPage;
