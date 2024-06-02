import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Header = ({ childState, onChildStateChange }) => {
	const [isLogin, setIsLogin] = useState(false);
	const userId = sessionStorage.getItem("userId");
	const navigate = useNavigate();
	const [searchValue, setSearchValue] = useState("");

	const toggleState = (state) => {
		onChildStateChange(state);
	};

	const loginCheck = () => {
		if (userId == null) {
		} else {
			setIsLogin(true);
			toggleState(true);
		}
	};

	const handleLogOut = () => {
		sessionStorage.clear();
		setIsLogin(false);
		toggleState(false);
		navigate("/");
	};

	const toMypage = () => {
		navigate(`/mypage`);
	};

	const handleSearchValueChange = (e) => {
		setSearchValue(e.target.value);
	};

	const handleSearch = (e) => {
		e.preventDefault();
		const state = {
			target: searchValue,
		};
		console.log(state);
		navigate(`/boards/search`, { state });
	};

	useEffect(() => {
		loginCheck();
	}, []);

	return (
		<header className="bg-purple-500 py-4 px-4">
			<nav className="container mx-auto flex items-center justify-between">
				<a href="/" className="text-white font-bold text-xl">
					<span className="text-blue-400">CT</span>CareerTime
				</a>
				<div>
					<input
						type="text"
						placeholder="관심분야를 입력하세요"
						className="rounded-l-md px-4 py-2 border border-black-300 focus:outline-none focus:ring-2 focus:ring-gray-600"
						value={searchValue}
						onChange={handleSearchValueChange}
					/>
					<button
						onClick={handleSearch}
						className="bg-white rounded-r-md px-4 py-2 border border-black-300 hover:bg-gray-200 transition-colors duration-300"
					>
						검색
					</button>
				</div>
				<div className="flex items-center space-x-4">
					{isLogin ? (
						<Link className="text-white font-bold" to="/post">
							글쓰기
						</Link>
					) : (
						<></>
					)}
					{isLogin ? (
						<>
							<button onClick={handleLogOut} className="text-white font-bold">
								로그아웃
							</button>
							<button onClick={toMypage} className="text-white font-bold">
								마이페이지
							</button>
						</>
					) : (
						<>
							<a
								href="/signin"
								className="text-white hover:text-gray-300 transition-colors duration-300 font-bold"
							>
								로그인
							</a>
							<a
								href="/signup"
								className="text-white hover:text-gray-300 transition-colors duration-300 font-bold"
							>
								회원가입
							</a>
						</>
					)}
				</div>
			</nav>
		</header>
	);
};

export default Header;
