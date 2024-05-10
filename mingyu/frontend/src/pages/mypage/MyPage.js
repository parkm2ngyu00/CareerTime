import MainIcon from "../../img/man_icon.png";
import ProfileImg from "../../img/profile.jpg";
import CompanyImg from "../../img/company.png";
import Header from "../../layouts/Header";
import { useEffect, useState } from "react";
import MarkdownEditor from "./MarkdownEditor";

const initUserData = {
	userName: "Mingyu Park",
	userCompany: "단국대학교",
	userImg: "",
	userEmail: "parkm2ngyu00@naver.com",
	userInterest: ["#스프링", "#백엔드", "#데이터베이스"],
};

function MyPage() {
	const [userData, setUserData] = useState(initUserData);
	const [editMode, setEditMode] = useState(false);
	const [hashtagInput, setHashtagInput] = useState("");

	const handleInputChange = (e) => {
		e.preventDefault();
		setHashtagInput(e.target.value);
		if (e.key === " " && e.target.value.trim().startsWith("#")) {
			const newHashtag = e.target.value.trim();
			console.log(newHashtag);
			setUserData((prevUserData) => ({
				...prevUserData,
				["userInterest"]: [...prevUserData.userInterest, newHashtag],
			}));
			setHashtagInput("");
		}
	};

	const handleHashtagRemove = (index) => {
		const newHashtags = [...userData.userInterest];
		newHashtags.splice(index, 1);
		setUserData((prevUserData) => ({
			...prevUserData,
			["userInterest"]: newHashtags,
		}));
	};

	const handleEdit = () => {
		setEditMode(true);
	};

	const handleChange = (e) => {
		e.preventDefault();
		const { name, value } = e.target;
		console.log(name);
		console.log(value);
		setUserData((prevUserData) => ({
			...prevUserData,
			[name]: value,
		}));
		console.log(userData.userName);
	};

	const handleSave = () => {
		// 변경 정보 저장 로직 추가 (api 호출 등)
		console.log(userData);
		setEditMode(false);
	};

	return (
		<>
			<Header></Header>
			<main className="px-36 flex">
				<div className="flex w-1/4 h-full justify-center">
					<div className="p-4">
						<div className="flex flex-col items-center">
							<img
								src={ProfileImg}
								alt="Profile"
								className="w-64 h-64 rounded-full"
							/>
						</div>
						<div className="mt-10">
							{editMode ? (
								<div>
									<input
										type="text"
										name="userName"
										value={userData.userName}
										onChange={handleChange}
										className="border border-gray-400 p-2 rounded-md w-full mb-1"
										placeholder="username"
									/>
									<input
										type="text"
										name="userCompany"
										value={userData.userCompany}
										onChange={handleChange}
										className="border border-gray-400 p-2 rounded-md w-full mb-1"
										placeholder="company or university"
									/>
									<input
										type="text"
										name="userEmail"
										value={userData.userEmail}
										onChange={handleChange}
										className="border border-gray-400 p-2 rounded-md w-full mb-1"
										placeholder="email"
									/>
									<input
										type="text"
										id="hashtags"
										value={hashtagInput}
										onChange={handleInputChange}
										onKeyUp={handleInputChange}
										className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
										placeholder="#해시태그"
									/>
									<div className="flex flex-wrap mt-2">
										{userData.userInterest.map((hashtag, index) => (
											<div
												key={index}
												className="bg-blue-500 text-white rounded-full px-3 py-1 mr-2 mb-2 flex items-center"
											>
												<span>{hashtag}</span>
												<button
													type="button"
													onClick={() => handleHashtagRemove(index)}
													className="ml-2 text-white hover:text-gray-300 focus:outline-none"
												>
													&times;
												</button>
											</div>
										))}
									</div>
									<button
										onClick={handleSave}
										className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 w-full"
									>
										Save
									</button>
								</div>
							) : (
								<>
									<div>
										<h2 className="text-2xl font-bold">{userData.userName}</h2>
										<div className="flex items-center mt-3">
											<img src={CompanyImg} className="w-6 h-6" />
											<p className="text-gray-600">{userData.userCompany}</p>
										</div>
										<p className="text-gray-600">{userData.userEmail}</p>
										<div className="flex flex-wrap my-2">
											{userData.userInterest.map((item) => (
												<div className="bg-blue-400 text-white mr-2 p-1 rounded-md mb-2">
													{item}
												</div>
											))}
										</div>
									</div>
									<button
										onClick={handleEdit}
										className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-2 w-full"
									>
										Edit profile
									</button>
								</>
							)}
						</div>
					</div>
				</div>
				<div className="w-3/4 h-full border-2 mt-4 rounded-lg flex flex-col p-2">
					<MarkdownEditor></MarkdownEditor>
				</div>
			</main>
		</>
	);
}

export default MyPage;
