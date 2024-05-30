import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

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

	return (
		<div className="container mx-auto">
			<h1 className="text-2xl font-bold mb-4">게시판 목록</h1>
			<div className="grid gap-4">
				{posts.map((post) => (
					<Link
						key={post.post_id}
						to={`/boards/${post.post_id}`}
						className="bg-white rounded-lg shadow-md p-4 block hover:bg-gray-100 transition-colors duration-300"
					>
						<h2 className="text-xl font-bold">{post.title}</h2>
						<p className="text-gray-600">작성자: {post.user.username}</p>
						<p className="text-gray-600">작성일: {post.post_date}</p>
						<div className="flex flex-wrap">
							{post.hashtags.split(", ").map((hashtag, index) => (
								<span
									key={index}
									className="bg-blue-200 text-blue-800 rounded-full px-2 py-1 mr-2 mb-2"
								>
									{hashtag}
								</span>
							))}
						</div>
					</Link>
				))}
			</div>
		</div>
	);
};

export default PostList;
