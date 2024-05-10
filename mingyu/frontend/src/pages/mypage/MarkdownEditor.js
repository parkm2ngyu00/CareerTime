import React from "react";
import MDEditor from "@uiw/react-md-editor";
import { useState } from "react";

const initMarkdown = `**마크다운 형식으로 자유롭게 꾸며보세요**`;

function MarkdownEditor() {
	const [isEditing, setIsEditing] = useState(false);
	const [value, setValue] = useState(initMarkdown);

	const handleEdit = () => {
		// api 호출 코드 추가
		console.log(value);
		setIsEditing((isEditing) => !isEditing);
	};

	return (
		<div className="container">
			{isEditing ? (
				<MDEditor
					height={300}
					preview="edit"
					value={value}
					onChange={setValue}
				/>
			) : (
				<></>
			)}
			<button
				className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded my-2"
				onClick={handleEdit}
			>
				{isEditing ? "저장하기" : "수정하기"}
			</button>
			{isEditing ? <></> : <MDEditor.Markdown source={value} />}
		</div>
	);
}

export default MarkdownEditor;
