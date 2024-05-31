import { Suspense, lazy } from "react";
import { createBrowserRouter } from "react-router-dom";
import Posting from "../pages/posts/Posting";
import MyPage from "../pages/mypage/MyPage";
import Board from "../pages/posts/Board";
import SigninPage from "../pages/User/SigninPage";
import { ChatDetailPage } from "../pages/mypage/MyChat";
import PostList from "../pages/posts/PostList";
import SignupPage from "../pages/User/SignupPage";
import ModifyPost from "../pages/posts/ModifyPost";

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
		path: "/post/modify",
		element: (
			<Suspense fallback={Loading}>
				<ModifyPost />
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
	{
		path: "/boards",
		element: (
			<Suspense fallback={Loading}>
				<PostList />
			</Suspense>
		),
	},
	{
		path: "/boards",
		children: [
			{
				path: ":boardId",
				element: <Board />,
			},
		],
	},
	{
		path: "/signin",
		element: (
			<Suspense fallback={Loading}>
				<SigninPage />
			</Suspense>
		),
	},
	{
		path: "/signin",
		element: (
			<Suspense fallback={Loading}>
				<SigninPage />
			</Suspense>
		),
	},
	{
		path: "/signup",
		element: (
			<Suspense fallback={Loading}>
				<SignupPage />
			</Suspense>
		),
	},
	{
		path: "/chat",
		children: [
			{
				path: ":chatId",
				element: <ChatDetailPage />,
			},
		],
	},
]);

export default root;
