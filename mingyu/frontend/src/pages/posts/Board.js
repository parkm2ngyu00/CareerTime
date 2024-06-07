import React, { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import MDEditor from "@uiw/react-md-editor";
import Sticky from "react-sticky-box";

import ProfileImg from "../../img/profile.jpg";
import Header from "../../layouts/Header";
import CompanyImg from "../../img/company.png";
import ReviewSection from "./ReviewSection";
import axios from "axios";

const boardInitData = {
	title: "[토스뱅크 현직자] 자바 백엔드 개발 진로상담 도와드립니다",
	hashtags: ["#자바", "#스프링", "#백엔드"],
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

const content = `
반갑습니다. 저는 현재 토스뱅크에서 자바 백엔드 개발자로 근무하고 있습니다. 최근 취업 준비생들을 위한 진로 상담 경험을 공유하고자 이 글을 작성하게 되었습니다.

## 소개

- 이름: 홍길동
- 경력: 4년차 자바 백엔드 개발자
- 현재 직장: 토스뱅크
- 주요 업무: 결제 시스템, 금융 서비스 백엔드 개발

## 진로 상담 내용

### 1. 자바 백엔드 개발자가 되기 위한 길

자바 백엔드 개발자가 되기 위해서는 다음과 같은 기술 스택을 학습하는 것이 좋습니다.

- Java 프로그래밍 언어
- Spring Framework (Spring Boot, Spring MVC, Spring Data JPA 등)
- 데이터베이스 (SQL, RDBMS 등)
- 버전 관리 (Git, GitHub)
- 클라우드 환경 (AWS, GCP, Azure 등)
- 테스트 자동화 (JUnit, Mockito 등)

이외에도 객체지향 프로그래밍 (OOP), 디자인 패턴, 알고리즘, 자료구조 등의 기본 지식도 중요합니다.

### 2. 포트폴리오 준비

취업 준비 시 포트폴리오를 준비하는 것이 매우 중요합니다. 실제 프로젝트 경험을 쌓는 것이 가장 좋지만, 개인 프로젝트나 오픈 소스 기여 등을 통해서도 포트폴리오를 만들 수 있습니다.

포트폴리오에는 다음과 같은 내용을 포함시키는 것이 좋습니다.

- 프로젝트 개요 및 목적
- 사용한 기술 스택
- 구현한 핵심 기능
- 특이사항 및 어려웠던 점
- 성과 및 결과

### 3. 코딩 테스트 대비

많은 기업에서 코딩 테스트를 치르게 됩니다. 알고리즘과 자료구조에 대한 이해가 필수적이며, 다음과 같은 주제를 공부하는 것이 좋습니다.

- 배열, 링크드 리스트, 스택, 큐
- 정렬 알고리즘 (버블, 선택, 삽입, 퀵, 합병 등)
- 탐색 알고리즘 (이진 탐색, DFS, BFS 등)
- 동적 프로그래밍
- 그래프 이론

코딩 테스트 대비를 위해 LeetCode, HackerRank 등의 사이트를 활용하면 좋습니다.

### 4. 면접 준비

면접에서는 기술적인 부분뿐만 아니라 소프트 스킬, 문제 해결 능력, 커뮤니케이션 능력 등도 중요하게 평가됩니다. 다음과 같은 부분을 준비하는 것이 좋습니다.

- 자바 및 Spring 관련 개념 정리
- 프로젝트 경험 및 기술적 문제 해결 과정 설명 준비
- 회사 및 직무에 대한 이해도 높이기
- 질문 준비하기

면접 준비를 위해 모의 면접을 해보거나 선배 개발자들과 피드백을 받아보는 것도 좋은 방법입니다.

이상으로 자바 백엔드 개발자 진로 상담을 마치겠습니다. 더 궁금한 사항이 있다면 언제든 질문해 주시기 바랍니다. 여러분의 성공적인 취업을 기원합니다!`;

const userData = {
	userName: "Mingyu Park",
	userCompany: "단국대학교",
	userImg: "",
	userEmail: "parkm2ngyu00@naver.com",
	userInterest: ["#스프링", "#백엔드", "#데이터베이스"],
};

const Board = () => {
	const { boardId } = useParams();
	const [boardData, setBoardData] = useState();
	const [viewCommnet, setViewComment] = useState(false);
	const [isAdmin, setIsAdmin] = useState(false);
	const [isLogin, setIsLogin] = useState(false);
	const userId = sessionStorage.getItem("userId");

	const defaultBtn = useRef(null);
	const navigate = useNavigate();

	const clickContent = () => setViewComment(false);
	const clickCommnet = () => setViewComment(true);

	// boardId에 따라 데이터를 fetching하는 함수 (예시)
	const fetchBoardData = async (boardId) => {
		const response = await fetch(`http://localhost:8080/api/boards/${boardId}`);
		const data = await response.json();
		console.log(data);
		if (data.userinfo.useremail == sessionStorage.getItem("userEmail")) {
			setIsAdmin(true);
		}
		return data;
	};

	useEffect(() => {
		fetchBoardData(boardId)
			.then((data) => setBoardData(data))
			.catch((error) => console.error(error));
	}, [boardId]);

	if (!boardData) {
		return <div>Loading...</div>;
	}

	// api 가 이상함. 수정 필요
	const handleDelete = async (e) => {
		e.preventDefault();
		const response = await axios.delete(
			`http://localhost:8080/api/boards/${boardId}`
		);
		navigate("/boards");
	};

	const handleModify = async (e) => {
		e.preventDefault();
		const state = {
			title: boardData.title,
			hashtags: boardData.hashtags,
			content: boardData.content,
			boardId: boardId,
		};
		console.log(state);
		navigate("/post/modify", { state });
	};

	const handleToProfile = (e) => {
		e.preventDefault();
		const state = {
			userId: boardData.userinfo.user_id,
		};
		navigate("/otherpage", { state });
	};

	const handleLoginChange = (newState) => {
		setIsLogin(newState);
	};

	const handleCreateChat = async () => {
		const yourId = boardData.userinfo.user_id;

		const response = await axios.post(
			`http://localhost:8080/api/chats/${userId}/${yourId}`
		);

		console.log(response.data);

		navigate(`/chat/${response.data.room_id}`);
	};

	return (
		<>
			<Header
				childState={isLogin}
				onChildStateChange={handleLoginChange}
			></Header>
			<div className="flex px-20 pb-20">
				<div className="w-2/3 h-auto mr-10 relative">
					<h1 className="font-extrabold text-3xl my-7">{boardData.title}</h1>
					{isAdmin ? (
						<>
							<button
								onClick={handleModify}
								className="absolute right-16 rounded-lg px-3 py-1 bg-blue-500 hover:bg-blue-700 text-white"
							>
								수정
							</button>
							<button
								onClick={handleDelete}
								className="absolute right-0 rounded-lg px-3 py-1 bg-blue-500 hover:bg-blue-700 text-white"
							>
								삭제
							</button>
						</>
					) : (
						<></>
					)}

					<div className="flex mb-5">
						{boardData.hashtags.map((hashtag, index) => (
							<p
								key={index}
								className="rounded-full px-3 py-1 mr-2 bg-blue-400 text-white"
							>
								{hashtag}
							</p>
						))}
					</div>
					<div className="flex border-b-2 border-gray mb-5 ">
						{viewCommnet ? (
							<>
								<button
									ref={defaultBtn}
									onClick={clickContent}
									className="font-semibold text-2xl hover:text-blue-500 focus:text-blue-500 focus:border-blue-500 focus:border-b-2 mr-5"
								>
									상세 설명
								</button>
								<button
									onClick={clickCommnet}
									className="font-semibold text-2xl hover:text-blue-500 text-blue-500 border-blue-500 border-b-2"
								>
									후기
								</button>
							</>
						) : (
							<>
								<button
									ref={defaultBtn}
									onClick={clickContent}
									className="font-semibold text-2xl hover:text-blue-500 text-blue-500 border-blue-500 border-b-2 mr-5"
								>
									상세 설명
								</button>
								<button
									onClick={clickCommnet}
									className="font-semibold text-2xl hover:text-blue-500 focus:text-blue-500 focus:border-blue-500 focus:border-b-2"
								>
									후기
								</button>
							</>
						)}
					</div>
					{viewCommnet ? (
						<>
							<ReviewSection></ReviewSection>
						</>
					) : (
						<MDEditor.Markdown source={boardData.content} />
					)}
				</div>
				<div className="w-1/3">
					<div className="py-4">
						<div className="flex flex-col items-center py-5 border-2 border-black-300 rounded-md">
							<img
								src={`data:image/jpeg;base64,${boardData.userinfo.userimage}`}
								alt="Profile"
								className="w-2/3 aspect-square rounded-full"
							/>
							<div className="w-2/3 mt-5">
								<div>
									<h2 className="text-2xl font-bold">
										{boardData.userinfo.username}
									</h2>
									<div className="flex items-center mt-3">
										<img src={CompanyImg} className="w-6 h-6" />
										<p className="text-gray-600">
											{boardData.userinfo.usercompany}
										</p>
									</div>
									<p className="text-gray-600">
										{boardData.userinfo.useremail}
									</p>
									<div className="flex my-2">
										{boardData.userinfo.userinterest.map((item, index) => (
											<div
												key={index}
												className="bg-blue-400 text-white mr-2 p-1 rounded-md"
											>
												{item}
											</div>
										))}
									</div>
									<button
										onClick={handleToProfile}
										className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 w-full"
									>
										프로필 보러가기
									</button>
									<button
										onClick={handleCreateChat}
										className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 w-full"
									>
										채팅하기
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
