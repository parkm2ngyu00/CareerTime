import MainIcon from "../../img/man_icon.png";
import ProfileImg from "../../img/profile.jpg";
import CompanyImg from "../../img/company.png";
import Header from "../../layouts/Header";
import { useEffect, useState } from "react";
import MarkdownEditor from "./MarkdownEditor";
import ChatListPage from "./ChatListPage";
import axios from "axios";
import { useLocation } from "react-router-dom";

function OtherPage() {
	const [userData, setUserData] = useState(null);
	const [editMode, setEditMode] = useState(false);
	const [hashtagInput, setHashtagInput] = useState("");
	const [isChat, setIsChat] = useState(false);
	const userId = sessionStorage.getItem("userId");
	const [profileId, setProfileId] = useState(null);
	const location = useLocation();
	const otherId = location.state.userId;
	const [userImg, setUserImg] = useState(null);

	useEffect(() => {
		const fetchProfileData = async () => {
			try {
				const response = await fetch(
					`http://localhost:8080/api/profiles/${otherId}`
				);
				const data = await response.json();
				console.log(data);
				setUserData(data);
				setUserImg(data.userImg);
				setProfileId(data.profileId);
			} catch (error) {
				console.error("Error fetching profile data:", error);
			}
		};

		fetchProfileData();
	}, [userId]); // userId가 변경될 때마다 useEffect가 실행됩니다.

	const handleInputChange = (e) => {
		e.preventDefault();
		setHashtagInput(e.target.value);
		if (e.key === " " && e.target.value.trim().startsWith("#")) {
			const newHashtag = e.target.value.trim();
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
		setUserData((prevUserData) => ({
			...prevUserData,
			[name]: value,
		}));
	};

	const handleChat = (e) => {
		e.preventDefault();
		setIsChat((prev) => !prev);
	};

	const handleSave = async (e) => {
		e.preventDefault();
		// 변경 정보 저장 로직 추가 (api 호출 등)
		const putData = {
			companyName: userData.userCompany,
			position: "Student",
			hashtags: userData.userInterest,
			introduction: "introduction", // 수정필요 (api 고쳐지면)
			profilePicture: "updated_base64_encoded_image_string", // 추후 이미지 처리 코드 추가
		};
		console.log(putData);
		try {
			const response = await axios.put(
				`http://localhost:8080/api/profiles/${userId}`,
				putData
			);
			console.log(response.data);
		} catch (error) {
			console.error("Error:", error);
		}
		setEditMode(false);
	};
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
			{userData ? (
				<main className="px-36 flex">
					<div className="flex w-1/4 h-full justify-center">
						<div className="p-4">
							<div className="flex flex-col items-center">
								{userImg ? (
									<>
										<img
											src={`data:image/jpeg;base64,${userImg}`}
											alt="Profile"
											className="w-64 h-64 rounded-full"
										/>
									</>
								) : (
									<>Loading...</>
								)}
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
									</div>
								) : (
									<>
										<div>
											<h2 className="text-2xl font-bold">
												{userData.userName}
											</h2>
											<div className="flex items-center mt-3">
												<img src={CompanyImg} className="w-6 h-6" />
												<p className="text-gray-600">{userData.userCompany}</p>
											</div>
											<p className="text-gray-600">{userData.userEmail}</p>
											<div className="flex flex-wrap my-2">
												{userData.userInterest.map((item, index) => (
													<div
														key={index}
														className="bg-blue-400 text-white mr-2 px-2 py-1 rounded-full mb-2"
													>
														{item}
													</div>
												))}
											</div>
										</div>
									</>
								)}
							</div>
						</div>
					</div>
					<div className="w-3/4 h-full border-2 mt-4 rounded-lg flex flex-col p-2">
						{isChat ? (
							<ChatListPage></ChatListPage>
						) : (
							<MarkdownEditor
								userData={userData}
								isOther={true}
							></MarkdownEditor>
						)}
					</div>
				</main>
			) : (
				<></>
			)}
		</>
	);
}

export default OtherPage;
