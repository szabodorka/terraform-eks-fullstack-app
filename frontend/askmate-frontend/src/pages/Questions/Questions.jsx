import { useEffect, useState } from "react";
import Loading from "../../components/Loading/Loading.jsx";
import Question from "./Question.jsx";
import {useLocation, useNavigate} from "react-router-dom";
import Error from "../../components/Error/Error.jsx";
import "./Questions.css";

const Questions = () => {
    const location = useLocation();
    const { pathname } = location;
    const [loading, setLoading] = useState(true);
    const [questions, setQuestions] = useState(null);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const fetchAllQuestions = async () => {
        const response = await fetch("/api/question/all");
        if (!response.ok) {
            if (response.status === 404) {
                setError("Questions Not Found");
            } else if (response.status === 500) {
                setError("Internal Server Error")
            }
        }
        return response.json();
    };

    const fetchSearchedQuestions = async (searchTerm) => {
        const response = await fetch(`/api/question/search?searchTerm=${searchTerm}`);
        if (!response.ok) {
            if (response.status === 404) {
                setError("Question Not Found");
            } else if (response.status === 500) {
                setError("Internal Server Error")
            }
        }
        return response.json();
    }

    const deleteQuestion = async (id) => {
        const response = await fetch(`/api/question/${id}`, { method: "DELETE" });
        if (!response.ok) {
            if (response.status === 404) {
                setError("Question Not Found");
            } else if (response.status === 500) {
                setError("Internal Server Error")
            }
        }
    };

    const handleDelete = (id) => {
        deleteQuestion(id).then(() => {
            setQuestions((prev) => prev.filter((question) => question.id !== id));
        });
    };

    const handleClick = (id) => {
        navigate(`/u/${id}`);
    };

    useEffect(() => {
        if(pathname.includes("search")){
            const searchTerm = pathname.split("/").pop();
            fetchSearchedQuestions(searchTerm)
                .then((questions) => {
                    setLoading(false);
                    setQuestions(questions);
                })
        } else{
            fetchAllQuestions()
                .then((questions) => {
                    setLoading(false);
                    setQuestions(questions);
                })
        }
    }, [pathname]);

    if (loading) return <Loading />;

    return (
        <>
            {error && <Error errorMessage={error} />}
            <div className="questionsContainer">
                {questions.map((question) => {
                    const isOwner = question.userId.toString() === localStorage.getItem("askMate_UserId");
                    return(
                    <div key={question.id} className="questionCard" onClick={() => handleClick(question.id)}>
                        <Question
                            title={question.title}
                            description={question.description}
                            postDate={question.postDate}
                        />
                        {isOwner && <button onClick={() => handleDelete(question.id)}>Delete</button>}
                    </div>)
                })}
            </div>
        </>
    );
};

export default Questions;
