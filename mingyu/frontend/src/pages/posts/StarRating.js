import React, { useState } from "react";

const StarRating = ({ rating, setRating }) => {
	const handleClick = (index) => {
		setRating(index + 1);
	};

	return (
		<div className="flex space-x-1">
			{[...Array(5)].map((star, index) => (
				<svg
					key={index}
					onClick={() => handleClick(index)}
					className={`w-8 h-8 cursor-pointer ${
						index < rating ? "text-yellow-500" : "text-gray-300"
					}`}
					fill="currentColor"
					viewBox="0 0 20 20"
				>
					<path d="M9.049 2.927C9.198 2.372 9.803 2.372 9.951 2.927l1.137 3.683a1 1 0 00.95.69h3.898c.52 0 .736.673.327 1.019l-3.151 2.43a1 1 0 00-.364 1.118l1.137 3.683c.15.555-.39.99-.807.69l-3.151-2.43a1 1 0 00-1.173 0l-3.151 2.43c-.417.3-.957-.135-.807-.69l1.137-3.683a1 1 0 00-.364-1.118L2.737 8.319c-.409-.346-.193-1.019.327-1.019h3.898a1 1 0 00.95-.69L9.049 2.927z" />
				</svg>
			))}
		</div>
	);
};

export default StarRating;
