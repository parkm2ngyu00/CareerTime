import ReviewForm from "./ReviewForm";

const reviewInfo = {
	reviewCount: 3,
	averageRating: 4,
	reviewList: [
		{ name: "김철수", rating: 4, comment: "제품이 매우 좋았습니다!" },
		{ name: "박영희", rating: 5, comment: "서비스도 친절하고 좋았어요." },
		{ name: "이동해", rating: 3, comment: "가격이 조금 높은 편이에요." },
	],
};

const Review = ({ name, rating, comment }) => {
	const ratingStars = Array.from({ length: 5 }, (_, i) => (
		<span
			key={i}
			className={`text-xl ${i < rating ? "text-yellow-500" : "text-gray-300"}`}
		>
			&#9733;
		</span>
	));

	return (
		<div className="bg-white shadow-md rounded-lg p-4 mb-4">
			<div className="flex items-center mb-2">
				<div className="font-bold mr-2">{name}</div>
				<div className="flex">{ratingStars}</div>
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
					<h3 className="text-lg font-semibold">상품 후기</h3>
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
	return (
		<div className="container mx-auto py-4">
			<ReviewStats
				reviewCount={reviewInfo.reviewCount}
				averageRating={reviewInfo.averageRating}
			/>
			<ReviewForm />
			<div>
				{reviewInfo.reviewList.map((review, index) => (
					<Review
						key={index}
						name={review.name}
						rating={review.rating}
						comment={review.comment}
					/>
				))}
			</div>
		</div>
	);
};

export default ReviewSection;
