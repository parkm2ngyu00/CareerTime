import React, { useState } from "react";

const SigninPage = () => {
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");

	const handleSubmit = (e) => {
		e.preventDefault();
		// 로그인 로직 처리
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
							이메일
						</label>
						<input
							className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
							id="email"
							type="email"
							placeholder="이메일을 입력하세요"
							value={email}
							onChange={(e) => setEmail(e.target.value)}
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
							placeholder="비밀번호를 입력하세요"
							value={password}
							onChange={(e) => setPassword(e.target.value)}
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
