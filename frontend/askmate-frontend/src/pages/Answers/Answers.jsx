import {useEffect, useState} from 'react'
import Loading from "../../components/Loading/Loading.jsx";
import Error from "../../components/Error/Error.jsx";
import Answer from "./Answer.jsx";
import "./Answers.css"

const Answers = ({questionId}) => {
    const [answers, setAnswers] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const fetchAnswers = async () => {
        const response = await fetch(`/api/answer/question/${questionId}`);
        if (!response.ok) {
            if (response.status === 404) {
                setError("Answers Not Found");
            } else if (response.status === 500) {
                setError("Internal Server Error")
            }
        }
        return await response.json();
    }

    const deleteAnswer = async (id) => {
        const response = await fetch(`/api/answer/${id}`, { method: "DELETE" });
        if (!response.ok) {
            if (response.status === 404) {
                setError("Answer Not Found");
            } else if (response.status === 500) {
                setError("Internal Server Error")
            }
        }
    };

    const handleDelete = (id) => {
        deleteAnswer(id).then(() => {
            setAnswers((prev) => prev.filter((answer) => answer.id !== id));
        });
    };

    useEffect(() => {
        setLoading(true);
        const fetchData = async () => {
            try {
                const data = await fetchAnswers();
                setAnswers(data);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        }
        fetchData();
    }, [questionId])

    if (loading) return <Loading />;

    return (
        <>
            {error && <Error errorMessage={error} />}
            <div className="answersContainer">
                {answers.map((answer) => {
                    const isOwner = answer.userId.toString() === localStorage.getItem("askMate_UserId");
                    return (
                        <div key={answer.id} className="answerCard">
                            <Answer
                                id={answer.id}
                                title={answer.title}
                                message={answer.message}
                                postDate={answer.postDate}
                            />
                            {isOwner && (
                                <button onClick={() => handleDelete(answer.id)}>Delete</button>
                            )}
                        </div>
                    );
                })}
            </div>
        </>
    );
}

export default Answers;