import { useEffect, useState } from "react";
import Loading from "../../components/Loading/Loading.jsx";
import Question from "./Question.jsx";

const fetchQuestions = async () => {
    const response = await fetch("/api/question/all");
    if (!response.ok) {
        throw new Error(`Failed to fetch questions. ${response.statusText}`);
    }
    return response.json();
};

const deleteQuestion = async (id) => {
    const response = await fetch(`/api/questions/${id}`, { method: "DELETE" });
    if (!response.ok) {
        throw new Error(`Failed to delete question. ${response.statusText}`);
    }
};

const Questions = () => {
    const [loading, setLoading] = useState(true);
    const [questions, setQuestions] = useState(null);

    const handleDelete = (id) => {
        deleteQuestion(id).then(() => {
            setQuestions((prev) => prev.filter((q) => q.id !== id));
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
            {questions.map((question) => (
                <div key={question.id}>
                    <Question
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