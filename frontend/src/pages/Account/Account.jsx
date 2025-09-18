import "./Account.css";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import Error from "../../components/Error/Error.jsx";
import Loading from "../../components/Loading/Loading.jsx";

async function fetchUser(id){
    const response = await fetch(`/api/user/${id}`);
    if(!response.ok){
        if(response.status === 500){
            throw new Error("Something went wrong with the server!");
        } else if (response.status === 404) {
            throw new Error("User not found!");
        }
    }
    return await response.json();
}
async function deleteUser(user){
    const response = await fetch(`/api/user/${user.id}`,{method: 'DELETE'});
    if(!response.ok){
        if(response.status === 500){
            throw new Error("Something went wrong with the server!");
        } else if (response.status === 404) {
            throw new Error("User not found!");
        }
    }
}



async function handleLogOut(navigate){
    localStorage.removeItem('askMate_UserId');
    localStorage.removeItem('askMate_userName');
    localStorage.removeItem('askMate_Password');
    navigate('/');
}


async function handleDelete(navigate, user, setError){
    try {
        await deleteUser(user);
        localStorage.removeItem('askMate_UserId');
        localStorage.removeItem('askMate_userName');
        localStorage.removeItem('askMate_Password');
        navigate('/');
    } catch (e){
        setError(e);
    }
}

function Account(){
    const [loading, setLoading] = useState(true);
    const [user, setUser] = useState(null);
    const navigate = useNavigate();
    const [error, setError] = useState(null);


    useEffect(() => {
        async function initUser(id){
            try {
                const user = await fetchUser(id);
                setUser(user);
                setLoading(false);
            } catch (e){
                setError(e);
            }
        }
        const id = localStorage.getItem('askMate_UserId');
        initUser(id);
    }, []);

    if (loading) return <Loading />;

    return (
        <div className="account-container">
            {error && <Error errorMessage={error} />}
            <h1 className="account-heading">
                Account
            </h1>
            <div>
                <table className="user-table">
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>ID</th>
                            <th>Registration date</th>
                            <th>Score</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>{user.username}</td>
                            <td>{user.id}</td>
                            <td>{new Date(user.registrationDate).toLocaleDateString('en-US', {
                                year: 'numeric',
                                month: 'long',
                                day: 'numeric'
                            })}</td>
                            <td>{user.score}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div className="button-group">
                <button className="delete-btn" onClick={()=> handleDelete(navigate, user, setError)}>
                    Delete Account
                </button>
                <button className="logout-btn" onClick={()=> handleLogOut(navigate)}>
                    Log Out
                </button>
            </div>
        </div>
    );
}

export default Account;