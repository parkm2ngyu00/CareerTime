import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const initPostData = {
	companyName: "회사명을 입력해주세요",
	position: "Developer",
	hashtags: ["#해시태그", "#입력"],
	introduction: "Hello, I'm a developer.",
	profilePicture: "base64_encoded_image_string",
};

const SignupPage = () => {
	const navigate = useNavigate();
	const [formData, setFormData] = useState({
		username: "",
		password: "",
		name: "",
		email: "",
		user_type: "USER",
	});
	const [isLoading, setIsLoading] = useState(false);
	const [error, setError] = useState(null);

	const handleChange = (e) => {
		setFormData({ ...formData, [e.target.name]: e.target.value });
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		setIsLoading(true);
		setError(null);
		console.log(formData);
		try {
			const response = await axios.post(
				"http://localhost:8080/api/users/register",
				formData
			);
			// sessionStorage.setItem("userId", response.data.user_id);
			// 회원가입 성공 후 추가 작업 수행 (예: 로그인 페이지로 이동)
			const response2 = await axios.post(
				`http://localhost:8080/api/profiles?userId=${response.data.user_id}`,
				initPostData
			);
			navigate("/signin");
		} catch (err) {
			setError("회원가입 중 오류가 발생했습니다.");
			console.error(err);
		} finally {
			setIsLoading(false);
		}
	};

	return (
		<div className="max-w-md mx-auto mt-8">
			<h2 className="text-2xl font-bold mb-4">회원가입</h2>
			{error && <p className="text-red-500 mb-4">{error}</p>}
			<form
				onSubmit={handleSubmit}
				className="bg-white shadow-md rounded px-8 pt-6 pb-8"
			>
				<div className="mb-4">
					<label
						htmlFor="username"
						className="block text-gray-700 font-bold mb-2"
					>
						아이디
					</label>
					<input
						type="text"
						id="username"
						name="username"
						value={formData.username}
						onChange={handleChange}
						className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
						required
					/>
				</div>
				<div className="mb-4">
					<label
						htmlFor="password"
						className="block text-gray-700 font-bold mb-2"
					>
						비밀번호
					</label>
					<input
						type="password"
						id="password"
						name="password"
						value={formData.password}
						onChange={handleChange}
						className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
						required
					/>
				</div>
				<div className="mb-4">
					<label htmlFor="name" className="block text-gray-700 font-bold mb-2">
						이름
					</label>
					<input
						type="text"
						id="name"
						name="name"
						value={formData.name}
						onChange={handleChange}
						className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
						required
					/>
				</div>
				<div className="mb-4">
					<label htmlFor="email" className="block text-gray-700 font-bold mb-2">
						이메일
					</label>
					<input
						type="email"
						id="email"
						name="email"
						value={formData.email}
						onChange={handleChange}
						className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
						required
					/>
				</div>
				<div className="flex items-center justify-between">
					<button
						type="submit"
						disabled={isLoading}
						className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
					>
						{isLoading ? "회원가입 중..." : "회원가입"}
					</button>
				</div>
			</form>
		</div>
	);
};

export default SignupPage;
