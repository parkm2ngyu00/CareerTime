import MainIcon from "../../img/man_icon.png";
import ProfileImg from "../../img/profile.jpg";
import CompanyImg from "../../img/company.png";
import Header from "../../layouts/Header";
import { useEffect, useState } from "react";
import MarkdownEditor from "./MarkdownEditor";

const userData = {
	userName: "Mingyu Park",
	userCompany: "단국대학교",
	userImg: "",
	userEmail: "parkm2ngyu00@naver.com",
	userInterest: ["#스프링", "#백엔드", "#데이터베이스"],
};

const userIntroduce = {
	userInterest: [
		"저는 스프링부트와 백엔드 개발, 그리고 데이터베이스에 대해 관심을 가지고 공부하고 있는 주니어 개발자입니다.",
		"현재는 이 세 가지 기술을 통해 다양한 프로젝트를 경험하고 있습니다. 스프링부트는 자바 기반의 웹 애플리케이션을 빠르고 간편하게 개발할 수 있는 프레임워크로, 이를 통해 효율적인 서버 사이드 개발을 경험하고 있습니다. 스프링 프레임워크의 다양한 기능들을 활용하면서 의존성 주입(Dependency Injection)과 관점 지향 프로그래밍(Aspect-Oriented Programming) 등의 개념을 심도있게 이해하고 있습니다.",
		"백엔드 개발은 사용자와 직접적으로 상호작용하는 서버 측의 로직을 구현하는 것인데, 이를 통해 데이터의 처리와 유효성 검사, 비즈니스 로직의 구현 등 다양한 기술적 과제에 도전하고 있습니다. RESTful API를 설계하고 구현하며 클라이언트와의 효율적인 통신을 지원하고 있습니다.",
		"마지막으로 데이터베이스는 서버에서 사용되는 데이터를 효율적으로 관리하고 조작하는 데 필수적입니다. SQL을 사용하여 데이터를 쿼리하고 관계형 데이터베이스를 설계하고 있으며, NoSQL 데이터베이스도 학습 중에 있습니다. 데이터 모델링과 성능 최적화에 대한 이해를 키워가고 있습니다.",
		"이렇듯 스프링부트, 백엔드 개발, 그리고 데이터베이스에 대한 지식과 경험을 쌓아가며, 더 나은 소프트웨어 엔지니어로 성장하고자 노력하고 있습니다. 함께 일하게 된다면 열정적으로 프로젝트에 기여할 것을 약속드립니다. 감사합니다!",
	],
	userDoing: [
		"저는 백엔드 개발자로서 사용자와 직접적으로 상호작용하는 서버 측의 로직을 개발하는 것에 흥미를 느끼고 있습니다. 스프링부트와 같은 프레임워크를 활용하여 효율적이고 안정적인 서비스를 구축하고, RESTful API를 설계하고 구현하는 과정에서 즐거움을 느끼고 있습니다. 또한 데이터베이스를 효율적으로 활용하여 데이터의 처리와 관리를 수행하는 과정에서 데이터 모델링과 성능 최적화에 대한 도전을 즐깁니다.",
		"제가 꿈꾸는 일은 사용자들에게 가치를 제공하고 혁신적인 서비스를 만들어내는 일입니다. 백엔드 개발을 통해 사용자 경험을 향상시키고, 데이터베이스를 효율적으로 활용하여 안정적이고 확장 가능한 시스템을 구축하는 일에 헌신하고 싶습니다. 또한 지속적인 학습과 기술적인 도전을 통해 성장하고, 함께 일하는 동료들과의 협업을 통해 더 나은 결과물을 만들어내는 것이 제 목표입니다.",
		"이러한 이유로 백엔드 개발 및 데이터베이스 관련 분야에서 일하고, 기술적인 역량을 발전시키며 혁신적인 서비스를 만들어내는 일에 기여하고 싶습니다. 함께 일하게 된다면 열정적으로 프로젝트에 참여하고, 끊임없는 성장과 협업을 통해 함께 발전해 나가겠습니다. 감사합니다.",
	],
};

const chatList = [];

function MyPage() {
	return (
		<>
			<Header></Header>
			<main className="px-36 flex">
				<div className="flex w-1/4 h-full justify-center">
					<div className="p-4">
						<div className="flex flex-col items-center">
							<img
								src={ProfileImg}
								alt="Profile"
								className="w-64 h-64 rounded-full "
							/>
						</div>
						<div className="mt-10">
							<div>
								<h2 className="text-2xl font-bold">{userData.userName}</h2>
								<div className="flex items-center mt-3">
									<img src={CompanyImg} className="w-6 h-6" />
									<p className="text-gray-600">{userData.userCompany}</p>
								</div>
								<p className="text-gray-600">{userData.userEmail}</p>
								<div className="flex my-2">
									{userData.userInterest.map((item) => (
										<div className="bg-blue-400 text-white mr-2 p-1 rounded-md">
											{item}
										</div>
									))}
								</div>
							</div>
							<button className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mt-4 w-full">
								Edit profile
							</button>
						</div>
					</div>
				</div>
				<div className="w-3/4 h-full border-2 mt-4 rounded-lg flex flex-col p-2">
					<MarkdownEditor></MarkdownEditor>
				</div>
			</main>
		</>
	);
}

export default MyPage;
