import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Error from "../../components/Error/Error";
import "./Register.css";

export default function Registration() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    async function handleSubmit(e) {
        e.preventDefault();
        if (username === "" || password === "") {
            setError("Must fill out all information!");
            return;
        }
        const user = {
            username: username,
            password: password
        };
        const res = await fetch("/api/user/", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(user),
        });
        if (!res.ok) {
            if(res.status === 500){
                setError("Something went wrong with the server!");
            }else{
                setError("Username already exists!");
            }
            return;
        }
        navigate("/");
    }

    return (
        <div className="registration-page">
            {error && <Error errorMessage={error} />}
            <div className="registration">
                <form onSubmit={handleSubmit}>
                    <label>
                        Enter your username:
                        <input type="text" onChange={(e) => setUsername(e.target.value)} />
                    </label>
                    <label>
                        Enter your password:
                        <input type="password" onChange={(e) => setPassword(e.target.value)} />
                    </label>
                    <button id="create-btn" type="submit">Create Account</button>
                    <button id="back-btn" type="button" onClick={() => navigate("/")}>
                        Back
                    </button>
                </form>
            </div>
        </div>
    );
}
