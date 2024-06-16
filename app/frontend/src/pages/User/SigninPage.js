import axios from "axios";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

// const initPostData = {
// 	companyName: "Example Company",
// 	position: "Developer",
// 	hashtags: ["#example1", "#example2"],
// 	introduction: "Hello, I'm a developer.",
// 	profilePicture: "base64_encoded_image_string",
// };

const SigninPage = () => {
	const navigate = useNavigate();
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState(null);
	const [formData, setFormData] = useState({
		username: "",
		password: "",
	});

	const handleSubmit = async (e) => {
		e.preventDefault();
		// 로그인 로직 처리
		try {
			const response = await axios.post(
				"http://localhost:8080/api/users/login",
				formData
			);
			sessionStorage.setItem("userId", response.data.user_id);
			sessionStorage.setItem("userName", response.data.name);
			sessionStorage.setItem("userEmail", response.data.email);
			// const response2 = await axios.post(
			// 	`http://localhost:8080/api/profiles?userId=${response.data.user_id}`,
			// 	initPostData
			// );
			console.log(response.data);
			navigate("/");
		} catch (err) {
			setError("로그인에 실패하였습니다.");
		}
	};

	const handleChange = (e) => {
		setFormData({ ...formData, [e.target.name]: e.target.value });
	};

	return (
		<div className="flex items-center justify-center h-screen bg-gray-100">
			<div className="w-full max-w-md">
				<form
					onSubmit={handleSubmit}
					className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4"
				>
					<div className="mb-4">
						<label
							className="block text-gray-700 font-bold mb-2"
							htmlFor="email"
						>
							아이디
						</label>
						<input
							className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							id="username"
							name="username"
							placeholder="이메일을 입력하세요"
							value={formData.username}
							onChange={handleChange}
						/>
					</div>
					<div className="mb-6">
						<label
							className="block text-gray-700 font-bold mb-2"
							htmlFor="password"
						>
							비밀번호
						</label>
						<input
							className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
							id="password"
							type="password"
							name="password"
							placeholder="비밀번호를 입력하세요"
							value={formData.password}
							onChange={handleChange}
						/>
					</div>
					<div className="flex items-center justify-between">
						<button
							className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
							type="submit"
						>
							로그인
						</button>
						<a
							className="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800"
							href="#"
						>
							비밀번호를 잊으셨나요?
						</a>
					</div>
				</form>
			</div>
		</div>
	);
};

export default SigninPage;
