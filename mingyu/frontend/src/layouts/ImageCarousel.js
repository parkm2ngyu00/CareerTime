import React, { useState, useEffect } from "react";
import Banner1 from "../img/banner_1.png";
import Banner2 from "../img/banner_2.png";

const ImageCarousel = () => {
	const [currentImageIndex, setCurrentImageIndex] = useState(0);

	const images = [Banner1, Banner2];

	useEffect(() => {
		const intervalId = setInterval(() => {
			setCurrentImageIndex((prevIndex) => (prevIndex + 1) % images.length);
		}, 5000);

		return () => {
			clearInterval(intervalId);
		};
	}, []);

	return (
		<div className="w-full">
			<div
				className="flex transition-transform duration-1000 ease-in-out"
				style={{ transform: `translateX(-${currentImageIndex * 100}%)` }}
			>
				{images.map((image, index) => (
					<img
						key={index}
						src={image}
						alt={`Banner ${index + 1}`}
						className="w-full h-auto flex-shrink-0 object-cover"
					/>
				))}
			</div>
		</div>
	);
};

export default ImageCarousel;
