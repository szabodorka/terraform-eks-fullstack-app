import { useEffect, useState } from "react";
import Loading from "../../components/Loading/Loading.jsx";
import Question from "./Question.jsx";
import Error from "../../components/Error/Error.jsx";



const deleteQuestion = async (id) => {
    const response = await fetch(`/api/question/${id}`, { method: "DELETE" });
    if (!response.ok) {
        throw new Error(`Failed to delete question. ${response.statusText}`);
    }
};

const Questions = () => {
    const [loading, setLoading] = useState(true);
    const [questions, setQuestions] = useState(null);
    const [error, setError] = useState(null);

    const fetchQuestions = async () => {
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

    const handleDelete = (id) => {
        deleteQuestion(id).then(() => {
            setQuestions((prev) => prev.filter((question) => question.id !== id));
        });
    };

    useEffect(() => {
        fetchQuestions()
            .then((questions) => {
                setLoading(false);
                setQuestions(questions);
            })
    }, []);

    if (loading) return <Loading />;

    return (
        <>
            {error && <Error errorMessage={error} />}
            {questions.map((question) => (
                <div key={question.id}>
                    <Question
                        id={question.id}
                        title={question.title}
                        description={question.description}
                    />
                    <button onClick={() => handleDelete(question.id)}>Delete</button>
                </div>
            ))}
        </>
    );
};

export default Questions;