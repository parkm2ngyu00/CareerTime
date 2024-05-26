import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import ManIcon from "../../img/man_icon.png";
import LeftArrow from "../../img/left-arrow.png";

const chatList = [
	{
		id: 1,
		name: "홍길동",
		profileImage: "https://example.com/profile.jpg",
		lastChatDate: "2023-05-25 14:30",
		lastChatMessage: "안녕하세요, 반가워요!",
		messages: [
			{
				sender: "other",
				content: "안녕하세요, 홍길동입니다.",
				time: "2023-05-25 14:20",
			},
			{
				sender: "me",
				content: "반갑습니다. 무엇을 도와드릴까요?",
				time: "2023-05-25 14:22",
			},
			{
				sender: "other",
				content: "제품 문의가 있습니다.",
				time: "2023-05-25 14:25",
			},
			// 더 많은 메시지 추가 가능
		],
	},
	{
		id: 2,
		name: "김철수",
		profileImage: "https://example.com/profile2.jpg",
		lastChatDate: "2023-05-24 10:15",
		lastChatMessage: "내일 회의 있습니다.",
		messages: [
			{
				sender: "other",
				content: "안녕하세요, 김철수입니다.",
				time: "2023-05-24 09:30",
			},
			{ sender: "me", content: "네, 안녕하세요.", time: "2023-05-24 09:32" },
			{
				sender: "other",
				content: "내일 회의 있습니다. 참석 가능하신가요?",
				time: "2023-05-24 10:10",
			},
			// 더 많은 메시지 추가 가능
		],
	},
	// 더 많은 채팅 목록 추가 가능
];

const ChatListPage = () => {
	const navigate = useNavigate();

	const handleChatClick = (chatId) => {
		navigate(`/chat/${chatId}`);
	};

	return (
		<div className="flex flex-col">
			<header className="bg-blue-500 text-white py-4 px-6">
				<h1 className="text-xl font-bold">채팅 목록</h1>
			</header>
			<main className="flex-1 overflow-y-auto bg-gray-100 p-4">
				{chatList.map((chat) => (
					<div
						key={chat.id}
						className="bg-white rounded-lg shadow-md mb-4 p-4 flex items-center cursor-pointer"
						onClick={() => handleChatClick(chat.id)}
					>
						<img
							src={ManIcon}
							alt={chat.name}
							className="w-12 h-12 rounded-full mr-4"
						/>
						<div className="flex-1">
							<h2 className="text-lg font-bold">{chat.name}</h2>
							<p className="text-gray-600">{chat.lastChatMessage}</p>
							<p className="text-gray-500 text-sm">{chat.lastChatDate}</p>
						</div>
					</div>
				))}
			</main>
		</div>
	);
};

const ChatDetailPage = () => {
	const { chatId } = useParams();
	const navigate = useNavigate();

	const chat = chatList.find((chat) => chat.id === parseInt(chatId));

	const handleGoBack = () => {
		navigate("/mypage");
	};

	if (!chat) {
		return <div>채팅 내용이 없습니다.</div>;
	}

	return (
		<div className="flex flex-col h-screen">
			<header className="bg-blue-500 text-white py-4 px-6 flex items-center">
				<button className="w-8 h-8 font-bold mr-5" onClick={handleGoBack}>
					<img src={LeftArrow} />
				</button>
				<img
					src={ManIcon}
					alt={chat.name}
					className="w-10 h-10 rounded-full mr-4"
				/>
				<h1 className="text-xl font-bold">{chat.name}</h1>
			</header>
			<main className="flex-1 overflow-y-auto bg-gray-100 p-4">
				{chat.messages.map((message, index) => (
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
						<span className="text-xs text-gray-500 ml-2">{message.time}</span>
					</div>
				))}
			</main>
		</div>
	);
};

export { ChatListPage, ChatDetailPage };
