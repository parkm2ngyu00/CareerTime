import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const Header = ({ isExpert }) => {
	const [isLogin, setIsLogin] = useState(false);
	const userId = sessionStorage.getItem("userId");
	const navigate = useNavigate();

	const loginCheck = () => {
		if (userId == null) {
		} else {
			setIsLogin(true);
		}
	};

	const handleLogOut = () => {
		sessionStorage.clear();
		setIsLogin(false);
		navigate("/");
	};

	const toMypage = () => {
		navigate(`/mypage`);
	};

	useEffect(() => {
		loginCheck();
	}, []);

	return (
		<header className="bg-purple-500 py-4 px-4">
			<nav className="container mx-auto flex items-center justify-between">
				<a href="/" className="text-white font-bold text-xl">
					<span className="text-blue-400">TC</span>TechCruit
				</a>
				<div>
					<input
						type="text"
						placeholder="관심분야를 입력하세요"
						className="rounded-l-md px-4 py-2 border border-gray-300 focus:outline-none focus:ring-2 focus:ring-purple-600"
					/>
					<button className="bg-white rounded-r-md px-4 py-2 border border-gray-300 hover:bg-gray-200 transition-colors duration-300">
						검색
					</button>
				</div>
				<div className="flex items-center space-x-4">
					{isLogin ? (
						<Link
							className="text-white hover:text-gray-300 transition-colors duration-300"
							to="/post"
							state={{ isExpert: isExpert }}
						>
							글쓰기
						</Link>
					) : (
						<></>
					)}
					{isLogin ? (
						<>
							<button
								onClick={handleLogOut}
								className="text-white hover:text-gray-300 transition-colors duration-300"
							>
								로그아웃
							</button>
							<button
								onClick={toMypage}
								className="text-white hover:text-gray-300 transition-colors duration-300"
							>
								마이페이지
							</button>
						</>
					) : (
						<>
							<a
								href="/signin"
								className="text-white hover:text-gray-300 transition-colors duration-300"
							>
								로그인
							</a>
							<a
								href="/signup"
								className="text-white hover:text-gray-300 transition-colors duration-300"
							>
								회원가입
							</a>
						</>
					)}

					<div className="bg-white rounded-full w-8 h-8 flex items-center justify-center text-purple-700 font-bold">
						<span>A</span>
					</div>
				</div>
			</nav>
		</header>
	);
};

export default Header;
