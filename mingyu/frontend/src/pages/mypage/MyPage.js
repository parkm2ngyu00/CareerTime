import MainIcon from "../../img/man_icon.png";
import ProfileImg from "../../img/profile.jpg";
import CompanyImg from "../../img/company.png";
import Header from "../../layouts/Header";
import { useEffect, useState } from "react";

const userData = {
	userName: "Mingyu Park",
	userCompany: "단국대학교",
	userImg: "",
	userEmail: "parkm2ngyu00@naver.com",
	userInterest: ["#스프링", "#백엔드", "#데이터베이스"],
};

const chatList = [];

function MyPage() {
	return (
		<>
			<Header></Header>
			<main className="px-36 h-screen flex">
				<div className="flex w-1/4 h-full justify-center">
					<div className="p-4">
						<div className="flex flex-col items-center">
							<img
								src={ProfileImg}
								alt="Profile"
								className="w-64 h-64 rounded-full "
							/>
						</div>
						<div className="mt-10">
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
							</div>
							<button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 w-full">
								Edit profile
							</button>
						</div>
					</div>
				</div>
				<div className="w-3/4 h-full border-2 mt-4 rounded-lg flex flex-col">
					<h2 className="font-bold text-2xl">진행중인 대화방</h2>
				</div>
			</main>
		</>
	);
}

export default MyPage;
