import React, { useState } from "react";
import StarRating from "./StarRating";
import axios from "axios";
import { useParams } from "react-router-dom";
import { comment } from "@uiw/react-md-editor";

const example = {
	user: {
		user_id: 1,
	},
	comment_rate: 4,
	comment_text: "Not Bad",
	comment_date: "2024-05-27T04:01:00",
};

const ReviewForm = () => {
	const [rating, setRating] = useState(0);
	const [review, setReview] = useState("");
	const { boardId } = useParams();
	const userId = sessionStorage.getItem("userId");

	const handleSubmit = async (e) => {
		e.preventDefault();
		console.log(boardId);
		const user = {
			user_id: parseInt(userId),
		};
		const postData = {
			user,
			comment_rate: rating,
			comment_text: review,
			comment_date: "2024-05-27T04:01:00",
		};
		console.log(postData);
		console.log(JSON.stringify(postData));
		try {
			const response = await axios.post(
				`http://localhost:8080/api/boards/${boardId}/comments`,
				postData,
				{
					headers: {
						"Content-Type": "application/json",
					},
				}
			);
			console.log(response.data);
		} catch (error) {
			console.error("Error:", error);
		}
	};

	return (
		<div className="mx-auto my-5 p-4 border rounded shadow-lg">
			<h1 className="text-xl font-semibold mb-4">후기 남기기</h1>
			<StarRating rating={rating} setRating={setRating} />
			<textarea
				className="w-full mt-4 p-2 border rounded"
				rows="4"
				value={review}
				onChange={(e) => setReview(e.target.value)}
				placeholder="후기 내용을 작성해주세요."
			/>
			<button
				type="submit"
				onClick={handleSubmit}
				className="mt-4 w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition duration-300"
			>
				제출하기
			</button>
		</div>
	);
};

export default ReviewForm;
