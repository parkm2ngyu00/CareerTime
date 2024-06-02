import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Header from "../../layouts/Header";

const PostList = () => {
	const [posts, setPosts] = useState([]);

	useEffect(() => {
		const fetchPosts = async () => {
			try {
				const response = await axios.get("http://localhost:8080/api/boards");
				setPosts(response.data);
			} catch (error) {
				console.error("Error fetching posts:", error);
			}
		};

		fetchPosts();
	}, []);

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
			<div className="container mx-auto max-w-6xl">
				<div className="mt-10 grid gap-4">
					{posts.map((post) => (
						<Link
							key={post.post_id}
							to={`/boards/${post.post_id}`}
							className="bg-white rounded-lg shadow-md px-4 block hover:bg-gray-100 transition-colors duration-300"
						>
							<h2 className="text-xl font-bold">{post.title}</h2>
							<p className="text-gray-600">작성자: {post.userinfo.username}</p>
							<p className="text-gray-600">작성일: {post.postdate}</p>
							<div className="flex flex-wrap my-3">
								{post.hashtags.map((hashtag, index) => (
									<span
										key={index}
										className="bg-blue-500 text-white rounded-full px-2 py-1 mr-2 mb-2"
									>
										{hashtag}
									</span>
								))}
							</div>
						</Link>
					))}
				</div>
			</div>
		</>
	);
};

export default PostList;
