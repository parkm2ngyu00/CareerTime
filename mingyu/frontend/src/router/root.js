import { Suspense, lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import todoRouter from "./todoRouter";
import Posting from "../pages/posts/Posting";
import MyPage from "../pages/mypage/MyPage";

const Loading = <div>Loading...</div>;
const Main = lazy(() => import("../pages/MainPage"));
const About = lazy(() => import("../pages/AboutPage"));
const TodoIndex = lazy(() => import("../pages/todo/IndexPage"));

const root = createBrowserRouter([
	{
		path: "",
		element: (
			<Suspense fallback={Loading}>
				<Main />
			</Suspense>
		),
	},
	{
		path: "/post",
		element: (
			<Suspense fallback={Loading}>
				<Posting />
			</Suspense>
		),
	},
	{
		path: "/mypage",
		element: (
			<Suspense fallback={Loading}>
				<MyPage />
			</Suspense>
		),
	},
]);

export default root;
