import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import Error from "../components/Error/Error.jsx";
import Loading from "../components/Loading/Loading.jsx";

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
        <div>
            {error && <Error errorMessage={error} />}
            <h1>
                Account
            </h1>
            <div>
                <table>
                    <tbody>
                        <tr>
                            <td>Name:</td>
                            <td>{user.username}</td>
                            <td>ID:</td>
                            <td>{user.id}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div>
                <button onClick={()=> handleDelete(navigate, user, setError)}>
                    Delete Account
                </button>
                <button onClick={()=> handleLogOut(navigate)}>
                    Log Out
                </button>
            </div>
        </div>
    );
}

export default Account;