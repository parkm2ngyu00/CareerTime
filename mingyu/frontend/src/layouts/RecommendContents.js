import React, { useState } from "react";

const contents = [
	{
		id: 1,
		title: "[EVENT 수퍼비전] 상담&견적 사 수퍼비전 1:1 맞춤형",
		description: "test description",
	},
	{
		id: 2,
		title: "test title",
		description: "test description",
	},
	{
		id: 3,
		title: "test title",
		description: "test description",
	},
	{
		id: 4,
		title: "test title",
		description: "test description",
	},
	{
		id: 5,
		title: "test title",
		description: "test description",
	},
	{
		id: 6,
		title: "test title",
		description: "test description",
	},
	{
		id: 7,
		title: "test title7",
		description: "test description",
	},
	{
		id: 8,
		title: "test title8",
		description: "test description",
	},
]; // Your list of contents here

const RecommendContents = () => {
	const [start, setStart] = useState(0);

	const handleNext = () => {
		setStart((prevStart) => Math.min(prevStart + 4, contents.length - 4));
	};

	const handlePrev = () => {
		setStart((prevStart) => Math.max(prevStart - 4, 0));
	};

	return (
		<section className="bg-gray-100 py-8 px-24">
			<h2 className="text-2xl font-bold mb-6">
				<span className="text-purple-500">추천 </span>상담 상품
			</h2>
			<div className="container mx-auto relative">
				<button
					className="bg-white rounded-full p-2 shadow-md w-10 h-10 absolute -left-12 top-1/3"
					onClick={handlePrev}
				>
					←
				</button>
				<div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
					{contents.slice(start, start + 4).map((content) => (
						<div className="bg-white rounded-lg shadow-md p-6" key={content.id}>
							<h3 className="text-lg font-semibold mb-2">{content.title}</h3>
							<p className="text-gray-600">{content.description}</p>
						</div>
					))}
				</div>
				<button
					className="bg-white rounded-full p-2 shadow-md w-10 h-10 absolute top-1/3 -right-12"
					onClick={handleNext}
				>
					→
				</button>
			</div>
		</section>
	);
};

export default RecommendContents;
