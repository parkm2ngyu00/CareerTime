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
		<div className="relative w-full h-96">
			{images.map((image, index) => (
				<img
					key={index}
					src={image}
					alt={`Banner ${index + 1}`}
					className={`absolute top-0 left-0 w-full h-full object-cover transition-opacity duration-1000 ${
						index === currentImageIndex ? "opacity-100" : "opacity-0"
					}`}
				/>
			))}
		</div>
	);
};

export default ImageCarousel;
