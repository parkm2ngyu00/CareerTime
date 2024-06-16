import { useLocation, useNavigate } from "react-router-dom";
import Header from "../../layouts/Header";
import { useEffect, useState } from "react";
import MDEditor from "@uiw/react-md-editor";
import axios from "axios";

const ModifyPost = () => {
	const userId = sessionStorage.getItem("userId");
	const navigate = useNavigate();
	const [hashtagInput, setHashtagInput] = useState("");
	const [hashtags, setHashtags] = useState([]);
	const location = useLocation();
	const [boardId, setBoardId] = useState(null);
	const [title, setTitle] = useState("");
	const [content, setContent] = useState(``);
	// const [value, setValue] = useState(``);
	const [isLogin, setIsLogin] = useState(false);
	const data = location.state;
	useEffect(() => {
		setTitle(data.title);
		setHashtags(data.hashtags);
		setContent(data.content);
		setBoardId(data.boardId);
	}, []);

	const handleTitleChange = (e) => {
		setTitle(e.target.value);
	};

	const handleContentChange = (e) => {
		setContent(e.target.value);
	};

	const handleInputChange = (e) => {
		setHashtagInput(e.target.value);
		if (e.key === " " && e.target.value.trim().startsWith("#")) {
			const newHashtag = e.target.value.trim();
			setHashtags([...hashtags, newHashtag]);
			setHashtagInput("");
		}
	};

	const handleHashtagRemove = (index) => {
		const newHashtags = [...hashtags];
		newHashtags.splice(index, 1);
		setHashtags(newHashtags);
	};

	const handleSubmit = async (e) => {
		e.preventDefault();

		const postData = {
			title,
			content,
			hashtags,
		};

		console.log(postData);
		// api 호출 코드
		try {
			const queryParams = {
				userId: userId,
			};
			const response = await axios.put(
				`http://localhost:8080/api/boards/${boardId}`,
				postData
			);
			console.log(response.data);
			navigate(`/boards/${response.data.post_id}`);
		} catch (error) {
			console.error("Error:", error);
		}
	};

	const handleLoginChange = (newState) => {
		setIsLogin(newState);
	};

	return (
		<>
			<Header
				childState={isLogin}
				onChildStateChange={handleLoginChange}
			></Header>
			<div className="w-3/5 mx-auto py-8">
				<div className="mb-6">
					<label htmlFor="title" className="block text-gray-700 font-bold mb-2">
						제목
					</label>
					<input
						type="text"
						id="title"
						value={title}
						onChange={handleTitleChange}
						className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
						placeholder="제목을 입력하세요"
					/>
				</div>

				<div className="mb-6">
					<label
						htmlFor="hashtags"
						className="block text-gray-700 font-bold mb-2"
					>
						해시태그
					</label>
					<input
						type="text"
						id="hashtags"
						value={hashtagInput}
						onChange={handleInputChange}
						onKeyUp={handleInputChange}
						className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
						placeholder="#해시태그 #입력"
					/>
					<div className="flex flex-wrap mt-2">
						{hashtags.map((hashtag, index) => (
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

				<div className="mb-6">
					<label
						htmlFor="content"
						className="block text-gray-700 font-bold mb-2"
					>
						본문 내용
					</label>
					<div className="container">
						<MDEditor
							height={300}
							visibleDragbar={false}
							preview="edit"
							value={content}
							onChange={(content) => setContent(content)}
						/>
					</div>
				</div>

				<button
					type="submit"
					onClick={handleSubmit}
					className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
				>
					수정하기
				</button>
			</div>
		</>
	);
};

export default ModifyPost;
