import React, { useState } from "react";
import { useEffect } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

const RecommendContents = () => {
	const [posts, setPosts] = useState([]);
	const navigate = useNavigate();
	const userId = sessionStorage.getItem("userId");

	useEffect(() => {
		const fetchPosts = async () => {
			try {
				const response = await axios.get(
					`http://localhost:8080/api/boards/recommend?userId=${userId}`
				);
				setPosts(response.data);
				console.log(response.data);
			} catch (error) {
				console.error("Error fetching posts:", error);
			}
		};

		fetchPosts();
	}, []);

	const handleGoList = (e) => {
		e.preventDefault();
		navigate("/boards");
	};

	return (
		<section className="bg-gray-100 py-8 px-24">
			<div className="container mx-auto">
				<div className="container mx-auto max-w-6xl">
					<div className="flex align-center justify-between">
						<h2 className="text-2xl font-bold">
							<span className="text-purple-500">추천 </span>게시글
						</h2>
						<button
							onClick={handleGoList}
							className="bg-purple-500 text-white font-bold p-2 rounded-lg"
						>
							전체보기
						</button>
					</div>

					<div className="mt-5 grid gap-4">
						{posts ? (
							<>
								{posts.slice(0, 5).map((post) => (
									<Link
										key={post.post_id}
										to={`/boards/${post.post_id}`}
										className="bg-white rounded-lg shadow-md px-4 block hover:bg-gray-100 transition-colors duration-300 py-3"
									>
										<h2 className="text-xl font-bold">{post.title}</h2>
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
										<p className="text-gray-600">
											작성자: {post.userinfo.username}
										</p>
										<p className="text-gray-600">작성일: {post.postdate}</p>
									</Link>
								))}
							</>
						) : (
							<></>
						)}
					</div>
				</div>
			</div>
		</section>
	);
};

export default RecommendContents;
