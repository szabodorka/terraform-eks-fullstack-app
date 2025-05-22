import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import './main.css'
import Login from "./pages/Login/Login.jsx";
import Registration from "./pages/Register/Register.jsx";
import Questions from "./pages/Questions/Questions.jsx";
import Menu from "./components/Menu/Menu.jsx";
import QuestionCreator from "./pages/QuestionCreator/QuestionCreator.jsx";
import QuestionDetails from "./pages/QuestionDetails/QuestionDetails.jsx";
import Account from "./pages/Account/Account.jsx";

const router = createBrowserRouter([
    {
        path: "/",
        element: <Login />,
    },
    {
        path: "/register",
        element: <Registration />,
    },
    {
        path: "/u",
        element: <Menu />,
        children: [
            {
                path: "/u/questions",
                element: <Questions />,
            },
            {
                path: "/u/search/:searchTerm",
                element: <Questions />,
            },
            {
                path: "/u/create",
                element: <QuestionCreator />,
            },
            {
                path: "/u/:id",
                element: <QuestionDetails />,
            },
            {
                path: "/u/account",
                element: <Account />,
            },
        ],
    },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>
);
