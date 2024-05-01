import { useState } from "react";
import Header from "../layouts/Header";
import RecentContents from "../layouts/RecentContents";
import RecommendContents from "../layouts/RecommendContents";

const MainPage = () => {
	const [isExpert, setIsExpert] = useState(true);
	return (
		<>
			{isExpert ? (
				<>
					<Header isExpert={isExpert}></Header>
					<RecommendContents></RecommendContents>
					<RecentContents></RecentContents>
				</>
			) : (
				<>
					<Header isExpert={isExpert}></Header>
					<RecommendContents></RecommendContents>
					<RecentContents></RecentContents>
				</>
			)}
		</>
	);
};

export default MainPage;
