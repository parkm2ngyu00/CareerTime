import { useEffect, useState } from "react";
import Header from "../layouts/Header";
import RecentContents from "../layouts/RecentContents";
import RecommendContents from "../layouts/RecommendContents";
import Banner1 from "../img/banner_1.png";
import Banner2 from "../img/banner_2.png";
import ImageCarousel from "../layouts/ImageCarousel";

const MainPage = () => {
	const [isLogin, setIsLogin] = useState(false);

	const handleLoginChange = (newState) => {
		setIsLogin(newState);
	};

	return (
		<>
			<Header
				childState={isLogin}
				onChildStateChange={handleLoginChange}
			></Header>
			<ImageCarousel />
			{isLogin ? (
				<>
					<RecommendContents></RecommendContents>
					<RecentContents></RecentContents>
				</>
			) : (
				<div className="relative">
					<div className="absolute top-0 left-0 right-0 bottom-0 bg-black bg-opacity-40 z-50 flex justify-center items-center backdrop-filter backdrop-blur-sm">
						<div className="text-white text-2xl font-bold">
							로그인 후에 이용해주세요
						</div>
					</div>
					<div className="opacity-50 pointer-events-none">
						<RecommendContents></RecommendContents>
						<RecentContents></RecentContents>
					</div>
				</div>
			)}
		</>
	);
};

export default MainPage;
