import { useEffect, useState } from "react";
import ReviewForm from "./ReviewForm";
import { useParams } from "react-router-dom";
import axios from "axios";
import StarRating from "./StarRating";

const reviewInfo = {
	reviewCount: 3,
	averageRating: 4,
	reviewList: [
		{ name: "김철수", rating: 4, comment: "제품이 매우 좋았습니다!" },
		{ name: "박영희", rating: 5, comment: "서비스도 친절하고 좋았어요." },
		{ name: "이동해", rating: 3, comment: "가격이 조금 높은 편이에요." },
	],
};

const Review = ({ name, rating, comment, date, author }) => {
	const [isMe, setIsMe] = useState(false);
	const ratingStars = Array.from({ length: 5 }, (_, i) => (
		<span
			key={i}
			className={`text-xl ${i < rating ? "text-yellow-500" : "text-gray-300"}`}
		>
			&#9733;
		</span>
	));

	useEffect(() => {
		const userId = sessionStorage.getItem("userId");
		if (userId == author) {
			setIsMe(true);
		}
	}, []);

	const handleDelete = (e) => {
		e.preventDefault();
	};

	return (
		<div className="bg-white shadow-md rounded-lg p-4 mb-4 relative">
			<div className="flex items-center mb-2">
				<div className="font-bold mr-2">{name}</div>
				<div className="flex">{ratingStars}</div>
				{isMe ? (
					<button
						onClick={handleDelete}
						className="text-gray-400 absolute right-3 top-7"
					>
						삭제
					</button>
				) : (
					<></>
				)}
				<div className="text-gray-400 absolute right-3 top-7">{date}</div>
			</div>
			<p className="text-gray-700">{comment}</p>
		</div>
	);
};

const ReviewStats = ({ reviewCount, averageRating }) => {
	return (
		<div className="bg-blue-100 rounded-lg p-4 mb-8">
			<div className="flex justify-between items-center">
				<div>
					<h3 className="text-lg font-semibold">상담 후기</h3>
					<p className="text-gray-600">총 {reviewCount}개의 후기</p>
				</div>
				<div className="flex items-center">
					<span className="text-3xl font-bold text-yellow-500 mr-2">
						{averageRating}
					</span>
					<div className="flex">
						{Array.from({ length: 5 }, (_, i) => (
							<span
								key={i}
								className={`text-xl ${
									i < Math.floor(averageRating)
										? "text-yellow-500"
										: "text-gray-300"
								}`}
							>
								&#9733;
							</span>
						))}
					</div>
				</div>
			</div>
		</div>
	);
};

const ReviewSection = () => {
	const [rating, setRating] = useState(0);
	const [review, setReview] = useState("");
	const { boardId } = useParams();
	const userId = sessionStorage.getItem("userId");

	function getCurrentFormattedDate() {
		const now = new Date();

		const year = now.getFullYear();
		const month = String(now.getMonth() + 1).padStart(2, "0");
		const date = String(now.getDate()).padStart(2, "0");
		const hours = String(now.getHours()).padStart(2, "0");
		const minutes = String(now.getMinutes()).padStart(2, "0");
		const seconds = String(now.getSeconds()).padStart(2, "0");

		return `${year}-${month}-${date}T${hours}:${minutes}:${seconds}`;
	}

	const fetchCommentData = async () => {
		try {
			const response = await fetch(
				`http://localhost:8080/api/boards/${boardId}/comments`
			);
			const data = await response.json();
			console.log(data);
			setComments(data);
			setCommentCount(data.length);
			if (data.length == 0) {
				setAverage(0);
			} else {
				const totalRating = data.reduce(
					(sum, comment) => sum + comment.rating,
					0
				);
				const averageRating = totalRating / data.length;
				const average = Number(averageRating.toFixed(1));
				setAverage(average);
			}
		} catch (error) {
			console.error("Error fetching profile data:", error);
		}
	};

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
			comment_date: getCurrentFormattedDate(),
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
			setReview("");
			setRating(0);
			fetchCommentData();
			console.log(response.data);
		} catch (error) {
			console.error("Error:", error);
		}
	};

	const [comments, setComments] = useState(null);
	const [commentCount, setCommentCount] = useState(0);
	const [average, setAverage] = useState(0);

	useEffect(() => {
		fetchCommentData();
	}, [boardId]);

	return (
		<>
			{comments ? (
				<div className="container mx-auto py-4">
					<ReviewStats reviewCount={commentCount} averageRating={average} />
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
					<div>
						{comments.map((review, index) => (
							<Review
								key={index}
								name={review.name}
								rating={review.rating}
								comment={review.comment}
								author={review.user_id}
								date=""
							/>
						))}
					</div>
				</div>
			) : (
				<></>
			)}
		</>
	);
};

export default ReviewSection;
