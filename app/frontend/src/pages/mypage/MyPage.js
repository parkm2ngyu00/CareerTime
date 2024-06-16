import ManIcon from "../../img/man_icon.png";
import ManIcon2 from "../../img/man_icon_2.png";
import ProfileImg from "../../img/profile.jpg";
import CompanyImg from "../../img/company.png";
import Header from "../../layouts/Header";
import { useEffect, useState } from "react";
import MarkdownEditor from "./MarkdownEditor";
import ChatListPage from "./ChatListPage";
import axios from "axios";

const initMarkdown = `**마크다운 형식으로 자유롭게 꾸며보세요**`;

function MyPage() {
	const [userData, setUserData] = useState(null);
	const [editMode, setEditMode] = useState(false);
	const [hashtagInput, setHashtagInput] = useState("");
	const [isChat, setIsChat] = useState(false);
	const userId = sessionStorage.getItem("userId");
	const [profileId, setProfileId] = useState(null);

	const [markdownValue, setMarkdownValue] = useState(initMarkdown);
	const [profilePicture, setProfilePicture] = useState(null);
	const [profilePicturePreview, setProfilePicturePreview] = useState(null);

	const handleMarkdownChange = (newValue) => {
		setMarkdownValue(newValue);
	};

	useEffect(() => {
		const fetchProfileData = async () => {
			try {
				const response = await fetch(
					`http://localhost:8080/api/profiles/${userId}`
				);
				const data = await response.json();
				console.log(data);
				setUserData(data);
				setMarkdownValue(data.userIntroduction);
				setProfileId(data.profileId);
				setProfilePicturePreview(data.userImg); // 기존 프로필 사진 설정
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
		let profilePictureBase64;

		if (profilePicture) {
			const fileReader = new FileReader();
			fileReader.readAsDataURL(profilePicture);
			fileReader.onload = () => {
				profilePictureBase64 = fileReader.result.split(",")[1];
				sendData(profilePictureBase64);
			};
		} else {
			sendData(null);
		}
	};

	const sendData = async () => {
		let profilePictureBase64;

		if (profilePicture) {
			const fileReader = new FileReader();
			fileReader.readAsDataURL(profilePicture);
			await new Promise((resolve) => {
				fileReader.onload = () => {
					// base64 인코딩된 데이터에서 'data:image/jpeg;base64,' 부분을 제거합니다.
					profilePictureBase64 = fileReader.result.split(",")[1];
					resolve();
				};
			});
		}

		const putData = {
			companyName: userData.userCompany,
			position: "Student",
			hashtags: userData.userInterest,
			introduction: markdownValue,
			profilePicture: profilePictureBase64,
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

	const handleProfilePictureChange = (event) => {
		const file = event.target.files[0];
		const reader = new FileReader();

		reader.onloadend = () => {
			// base64 인코딩된 데이터에서 'data:image/jpeg;base64,' 부분을 제거합니다.
			const base64Data = reader.result.split(",")[1];
			setProfilePicture(file);
			setProfilePicturePreview(base64Data);
			console.log(profilePicturePreview);
		};

		if (file) {
			reader.readAsDataURL(file);
		}
	};

	const [isLogin, setIsLogin] = useState(false);

	const handleLoginChange = (newState) => {
		setIsLogin(newState);
	};

	// useEffect(() => {
	// 	if (profilePicture) {
	// 		const reader = new FileReader();
	// 		reader.onloadend = () => {
	// 			setProfilePicturePreview(reader.result);
	// 		};
	// 		reader.readAsDataURL(profilePicture);
	// 	} else {
	// 		setProfilePicturePreview(null);
	// 	}
	// }, [profilePicture]);

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
								{editMode ? (
									<label htmlFor="profile-picture-upload">
										<img
											src={
												profilePicturePreview
													? `data:image/jpeg;base64,${profilePicturePreview}`
													: ManIcon2
											}
											alt="Profile"
											className="w-64 h-64 rounded-full cursor-pointer"
										/>
										<input
											id="profile-picture-upload"
											type="file"
											accept="image/*"
											onChange={handleProfilePictureChange}
											style={{ display: "none" }}
										/>
									</label>
								) : (
									<img
										src={
											profilePicturePreview
												? `data:image/jpeg;base64,${profilePicturePreview}`
												: ManIcon2
										}
										alt="Profile"
										className="w-64 h-64 rounded-full"
									/>
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
										<button
											onClick={handleSave}
											className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 w-full"
										>
											저장
										</button>
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
										<button
											onClick={handleEdit}
											className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-2 w-full"
										>
											프로필 수정하기
										</button>
										{isChat ? (
											<button
												onClick={handleChat}
												className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-2 w-full"
											>
												상세 프로필 보기
											</button>
										) : (
											<button
												onClick={handleChat}
												className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-2 w-full"
											>
												내 채팅 보기
											</button>
										)}
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
								value={markdownValue}
								onChange={handleMarkdownChange}
								userData={userData}
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

export default MyPage;
