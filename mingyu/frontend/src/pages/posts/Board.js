import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import MDEditor from "@uiw/react-md-editor";

import ProfileImg from "../../img/profile.jpg";
import Header from "../../layouts/Header";
import CompanyImg from "../../img/company.png";

// boardId에 따라 데이터를 fetching하는 함수 (예시)
const fetchBoardData = async (boardId) => {
	const response = await fetch(`/api/boards/${boardId}`);
	const data = await response.json();
	return data;
};

const boardInitData = {
	title: "[토스뱅크 현직자] 자바 백엔드 개발 진로상담 도와드립니다",
	hashtags: ["#test1", "#test2", "test3"],
	content: "# 제목\n안녕",
	postdate: "2024-05-05",
	userinfo: {
		username: "username",
		userimg: "",
		usercompany: "단국대학교",
		useremail: "parkm2ngyu00@naver.com",
		userinterest: ["#관심분야1", "#관심분야2", "#관심분야3"],
	},
};

const userData = {
	userName: "Mingyu Park",
	userCompany: "단국대학교",
	userImg: "",
	userEmail: "parkm2ngyu00@naver.com",
	userInterest: ["#스프링", "#백엔드", "#데이터베이스"],
};

const Board = () => {
	const { boardId } = useParams();
	const [boardData, setBoardData] = useState(boardInitData);
	const [value, setValue] = useState(boardInitData.content);

	// useEffect(() => {
	// 	fetchBoardData(boardId)
	// 		.then((data) => setBoardData(data))
	// 		.catch((error) => console.error(error));
	// }, [boardId]);

	if (!boardData) {
		return <div>Loading...</div>;
	}

	return (
		<>
			<Header></Header>
			<div className="flex">
				<div className="w-3/4 h-screen bg-red-300 px-4">
					<h1 className="font-extrabold text-3xl my-10">{boardData.title}</h1>
					<MDEditor.Markdown source={value} />
				</div>
				<div className="w-1/4 h-screen bg-purple-300">
					<div className="p-4">
						<div className="flex flex-col items-center">
							<img
								src={ProfileImg}
								alt="Profile"
								className="w-64 h-64 rounded-full "
							/>
							<div className="mt-5">
								<div>
									<h2 className="text-2xl font-bold">{userData.userName}</h2>
									<div className="flex items-center mt-3">
										<img src={CompanyImg} className="w-6 h-6" />
										<p className="text-gray-600">{userData.userCompany}</p>
									</div>
									<p className="text-gray-600">{userData.userEmail}</p>
									<div className="flex my-2">
										{userData.userInterest.map((item) => (
											<div className="bg-blue-400 text-white mr-2 p-1 rounded-md">
												{item}
											</div>
										))}
									</div>
									<button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 w-full">
										프로필 보러가기
									</button>
									<button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 w-full">
										구매하기
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</>
	);
};

export default Board;
