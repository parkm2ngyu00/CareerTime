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

const RecentContents = () => {
	return (
		<section className="bg-gray-100 py-8 px-24">
			<div className="container mx-auto">
				<h2 className="text-2xl font-bold mb-6">
					<span className="text-purple-500">최신 </span>상담 상품
				</h2>
				<div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
					{contents.map((content) => (
						<div className="bg-white rounded-lg shadow-md p-6" key={content.id}>
							<h3 className="text-lg font-semibold mb-2">{content.title}</h3>
							<p className="text-gray-600">{content.description}</p>
						</div>
					))}
				</div>
			</div>
		</section>
	);
};

export default RecentContents;
