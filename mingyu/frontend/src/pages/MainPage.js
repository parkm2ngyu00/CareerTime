import { useState } from "react";
import Header from "../layouts/Header";
import RecentContents from "../layouts/RecentContents";
import RecommendContents from "../layouts/RecommendContents";
import Banner1 from "../img/banner_1.png";
import Banner2 from "../img/banner_2.png";
import ImageCarousel from "../layouts/ImageCarousel";

const MainPage = () => {
	return (
		<>
			<Header></Header>
			<ImageCarousel />
			<RecommendContents></RecommendContents>
			<RecentContents></RecentContents>
		</>
	);
};

export default MainPage;
