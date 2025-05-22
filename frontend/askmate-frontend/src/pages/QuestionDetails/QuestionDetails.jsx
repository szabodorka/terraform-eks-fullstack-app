import "./QuestionDetails.css";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Loading from "../../components/Loading/Loading.jsx";
import Error from "../../components/Error/Error.jsx";

function QuestionDetails() {
    const location = useLocation();
    const { pathname } = location;
    const [loading, setLoading] = useState(true);
    const [question, setQuestion] = useState(null);
    const [error, setError] = useState(null);
    const [username, setUsername] = useState(null);

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
    }, []);

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
        </div>
    );
}

export default QuestionDetails;