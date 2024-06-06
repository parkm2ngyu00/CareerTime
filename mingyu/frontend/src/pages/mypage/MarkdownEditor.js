import React from "react";
import MDEditor from "@uiw/react-md-editor";
import { useState } from "react";
import axios from "axios";

const initMarkdown = `**마크다운 형식으로 자유롭게 꾸며보세요**`;

function MarkdownEditor({ isOther, value, onChange, userData }) {
	const userId = sessionStorage.getItem("userId");
	const [isEditing, setIsEditing] = useState(false);

	const handleChange = (newValue) => {
		onChange(newValue);
	};

	const handleSave = async (e) => {
		e.preventDefault();
		// 변경 정보 저장 로직 추가 (api 호출 등)
		const putData = {
			companyName: userData.userCompany,
			position: "Student",
			hashtags: userData.userInterest,
			introduction: value, // 수정필요 (api 고쳐지면)
			profilePicture: userData.userImg, // 추후 이미지 처리 코드 추가
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
		setIsEditing((isEditing) => !isEditing);
	};

	return (
		<div className="container">
			{isOther ? (
				<MDEditor.Markdown source={userData.userIntroduction} />
			) : (
				<>
					{isEditing ? (
						<MDEditor
							height={300}
							preview="edit"
							value={value}
							onChange={handleChange}
						/>
					) : (
						<></>
					)}
					<button
						className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded my-2"
						onClick={handleSave}
					>
						{isEditing ? "저장하기" : "수정하기"}
					</button>
					{isEditing ? <></> : <MDEditor.Markdown source={value} />}
				</>
			)}
		</div>
	);
}

export default MarkdownEditor;
