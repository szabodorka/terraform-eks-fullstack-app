import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import Error from "../../components/Error/Error";
import "./Login.css";

export default function Login() {
    const [username, setUsername] = useState(localStorage.getItem("askMate_Username") ?? "");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    async function handleSubmit(e) {
        e.preventDefault();
        const res = await fetch(`/api/user/login?username=${username}&password=${password}`);
        if(!res.ok){
            if(res.status === 500){
                setError("Something went wrong with the server!");
            }else{
                setError("Wrong username or password!");
            }
            return;
        }
        const userId = await res.json();
        localStorage.setItem("askMate_UserId", userId);
        localStorage.setItem("askMate_Username", username);
        localStorage.setItem("askMate_Password", password);
        navigate(`/u/questions`);
    }

    return (
        <div className="login-page">
            {error && <Error errorMessage={error} />}
            <div className="login">
                <form onSubmit={handleSubmit}>
                    <label>
                        Username:
                        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                    </label>
                    <label>
                        Password:
                        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
                    </label>
                    <button type="submit">Login</button>
                </form>
            </div>
            <div className="sign-up">
                <p>Don't have an account yet?</p>
                <Link className="link" to="/register">
                    <button>Sign up</button>
                </Link>
            </div>
        </div>
    );
}
