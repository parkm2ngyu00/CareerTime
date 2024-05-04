import React, { useState } from "react";
import ReactMarkdown from "react-markdown";
import TextareaAutosize from "react-textarea-autosize";
import remarkGfm from "remark-gfm";

const markdownText = `# 관심 분야
---
제 관심 분야는 스프링부트입니다.
![스프링부트이미지](https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FdagiIl%2FbtsdRhmZh8L%2FfueJb3KSKCL1h0JPOJk5e1%2Fimg.jpg)
스프링부트부트`;

const MarkdownEditor = () => {
	const [markdown, setMarkdown] = useState(markdownText);
	const [isEditing, setIsEditing] = useState(false);

	const handleChange = (event) => {
		setMarkdown(event.target.value);
	};

	const handleApply = () => {
		setIsEditing(false);
	};

	const handleEdit = () => {
		setIsEditing(true);
	};

	return (
		<div className="container mx-auto p-4">
			{isEditing ? (
				<>
					<div className="mb-4">
						<TextareaAutosize
							className="w-full border border-gray-300 rounded-md p-2"
							placeholder="마크다운 형식으로 작성하세요..."
							value={markdown}
							onChange={handleChange}
						/>
					</div>
					<div className="mb-4 flex justify-between">
						<div>
							<button
								className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mr-2"
								onClick={handleApply}
							>
								적용
							</button>
							<button
								className="bg-gray-500 hover:bg-gray-700 text-white font-bold py-2 px-4 rounded"
								onClick={() => setMarkdown("")}
							>
								초기화
							</button>
						</div>
					</div>
				</>
			) : (
				<div className="flex justify-end">
					<button
						className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
						onClick={handleEdit}
					>
						수정
					</button>
				</div>
			)}
			<div className="prose max-w-none">
				<ReactMarkdown
					components={{
						img(props) {
							const { node, ...rest } = props;
							return (
								<img
									style={{
										width: "300px",
										height: "300px",
									}}
									{...rest}
								/>
							);
						},
						br(props) {
							const { node, ...rest } = props;
							return (
								<div
									style={{
										marginBottom: "10px",
									}}
									{...rest}
									div
								/>
							);
						},
						p(props) {
							const { node, ...rest } = props;
							return (
								<p
									style={{
										fontSize: 18,
										color: "black",
									}}
									{...rest}
								/>
							);
						},
						hr(props) {
							const { node, ...rest } = props;
							return (
								<hr
									style={{
										height: "2px",
										backgroundColor: "purple",
										marginBottom: "5px",
									}}
									{...rest}
								/>
							);
						},
						// Map `h1` (`# heading`) to use `h2`s.
						// Rewrite `em`s (`*like so*`) to `i` with a red foreground color.
						h1(props) {
							const { node, ...rest } = props;
							return (
								<p
									style={{
										color: "purple",
										fontSize: 25,
										fontWeight: "bolder",
									}}
									{...rest}
								/>
							);
						},
					}}
					remarkPlugins={[remarkGfm]}
				>
					{markdown}
				</ReactMarkdown>
			</div>
		</div>
	);
};

export default MarkdownEditor;
