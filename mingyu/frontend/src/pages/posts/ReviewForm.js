import React, { useState } from "react";
import StarRating from "./StarRating";
import axios from "axios";

const ReviewForm = () => {
	const [rating, setRating] = useState(0);
	const [review, setReview] = useState("");
	// const userId = sessionStorage.getItem("userId");

	const handleSubmit = async (e) => {
		e.preventDefault();
		const postData = {
			review,
			rating,
		};
		console.log(postData);

		// try {
		// 	const response = await axios.post(`/api/comment?userId=${userId}`, {
		// 		rating: rating,
		// 		review: review,
		// 	});
		// 	if (response.status === 200) {
		// 		setRating(0);
		// 		setReview("");
		// 	} else {
		// 	}
		// } catch (error) {}
	};

	return (
		<form
			onSubmit={handleSubmit}
			className="mx-auto my-5 p-4 border rounded shadow-lg"
		>
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
				className="mt-4 w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition duration-300"
			>
				제출하기
			</button>
		</form>
	);
};

export default ReviewForm;
