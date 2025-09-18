import "./QuestionDetails.css";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Loading from "../../components/Loading/Loading.jsx";
import Error from "../../components/Error/Error.jsx";
import Answers from "../Answers/Answers.jsx";

function QuestionDetails() {
    const location = useLocation();
    const { pathname } = location;
    const [loading, setLoading] = useState(true);
    const [question, setQuestion] = useState(null);
    const [error, setError] = useState(null);
    const [username, setUsername] = useState(null);
    const [title, setAnswerTitle] = useState(null);
    const [description, setAnswerDesc] = useState(null);

    async function handlePostAnswer(title, description){
        const userId = localStorage.getItem("askMate_UserId");
        const answer = {
            title: title,
            message: description,
            userId: userId,
            questionId: question.id
        };
        const res = await fetch("/api/answer/", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(answer),
        });
        if(!res.ok){
            if(res.status === 500){
                setError("Something went wrong with the server!");
            }else{
                setError("Couldn't post answer!");
            }
        }
        if(parseInt(userId) === question.userId){
            setLoading(true);
            return;
        }
        await fetch(`/api/user/score?userId=${userId}&scoreDiff=${10}`);
        await fetch(`/api/user/score?userId=${question.userId}&scoreDiff=${1}`);
        setLoading(true);
    }

    useEffect(() => {
        async function fetchQuestion(id) {
            const response = await fetch(`/api/question/${id}`);
            if(!response.ok) {
                if(response.status === 500){
                    setError("Something went wrong with the server!");
                } else if (response.status === 404) {
                    setError("Question not found!");
                }
            }
            return await response.json();
        }
        async function fetchUsername(userId) {
            const response = await fetch(`/api/user/name/${userId}`);
            if(!response.ok) {
                if(response.status === 500){
                    setError("Something went wrong with the server!");
                } else if (response.status === 404) {
                    setError("User not found!");
                }
            }
            return await response.text();
        }
        const id = pathname.split("/").pop();
        fetchQuestion(id).then((question) => {
            fetchUsername(question.userId).then((username) => {
                setQuestion(question);
                setUsername(username);
                setLoading(false);
            });
        });
    }, [loading]);

    if(loading) return <Loading />;

    return (
        <div className="question-details">
            {error && <Error errorMessage={error} />}
            <h1>{question.title}</h1>
            <p>Posted at: {new Date(question.postDate).toLocaleDateString('en-US', {
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            })}</p>
            <p>Created by: {username}</p>
            <p>{question.description}</p>
            <div className="post-answer">
            <label>
                Post your answer:
                <label>
                    Title:
                    <input type="text" onChange={(e) => setAnswerTitle(e.target.value)} />
                </label>
                <label>
                    Description:
                    <input type="text" onChange={(e) => setAnswerDesc(e.target.value)} />
                </label>
                {title && description ? <button onClick={() => handlePostAnswer(title, description)}>Post</button> : <button disabled>Post</button>}
            </label>
            </div>
            <Answers questionId={question.id} />
        </div>
    );
}

export default QuestionDetails;