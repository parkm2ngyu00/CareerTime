import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

// boardId에 따라 데이터를 fetching하는 함수 (예시)
const fetchBoardData = async (boardId) => {
	const response = await fetch(`/api/boards/${boardId}`);
	const data = await response.json();
	return data;
};

const boardInitData = {
	title: "test title",
	hashtags: ["#test1", "#test2", "test3"],
	content: "test content",
	postdate: "2024-05-05",
	userinfo: {
		username: "username",
		usercompany: "단국대학교",
		useremail: "parkm2ngyu00@naver.com",
		userinterest: ["#관심분야1", "#관심분야2", "#관심분야3"],
	},
};

const Board = () => {
	const { boardId } = useParams();
	const [boardData, setBoardData] = useState(boardInitData);

	// useEffect(() => {
	// 	fetchBoardData(boardId)
	// 		.then((data) => setBoardData(data))
	// 		.catch((error) => console.error(error));
	// }, [boardId]);

	if (!boardData) {
		return <div>Loading...</div>;
	}

	return (
		<div>
			<h1>{boardData.title}</h1>
			<p>{boardData.content}</p>
			{boardData.userinfo.username}
			{/* 다른 필요한 내용 렌더링 */}
		</div>
	);
};

export default Board;
