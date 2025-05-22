import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Error from "../../components/Error/Error";

function QuestionCreator() {
    const [title, setTitle] = useState("");
    const [description, setDescription] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    async function handleSubmit(e) {
        e.preventDefault();
        const userId = localStorage.getItem("askMate_UserId");
        if (title === "" || description === "") {
            setError("Must fill out all information!");
            return;
        }
        const question = {
            title: title,
            description: description,
            userId: userId
        };
        const res = await fetch("/api/question/", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(question),
        });
        if(!res.ok){
            if(res.status === 500){
                setError("Something went wrong with the server!");
            } else{
                setError("Error while creating question!");
            }
            return;
        }
        await fetch(`/api/user/score?userId=${userId}&scoreDiff=${5}`);
        navigate("/u/questions");
    }

    return (
        <>
            {error && <Error errorMessage={error} />}
            <div className="questionCreator">
                <form onSubmit={handleSubmit}>
                    <label>
                        Title:
                        <input type="text" onChange={(e) => setTitle(e.target.value)} />
                    </label>
                    <label>
                        Description:
                        <input type="text" onChange={(e) => setDescription(e.target.value)} />
                    </label>
                    <button type="submit">Post Question</button>
                    <button type="button" onClick={() => navigate("/u/questions")}>
                        Back
                    </button>
                </form>
            </div>
        </>
    );
}

export default QuestionCreator;
