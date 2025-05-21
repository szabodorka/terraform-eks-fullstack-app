import {useNavigate} from "react-router-dom";
import {useState} from "react";

async function fetchUser(id){
    try{
        const response = await fetch(`/api/user/${id}`);
        const user = await response.json();
        return user;
    } catch (error){
        console.log(error);
    }
}
async function deleteUser(user){
    try{
        const response = await fetch(`/api/user/${id}`,{method: 'DELETE'});
        const user = await response.json();
        return user;
    } catch (error){
        console.log(error);
    }
}



async function handleLogOut(navigate){
    localStorage.removeItem('askMate_UserId');
    localStorage.removeItem('askMate_userName');
    localStorage.removeItem('askMate_Password');
    navigate('/');
}


async function handleDelete(navigate, user){
    localStorage.removeItem('askMate_UserId');
    localStorage.removeItem('askMate_userName');
    localStorage.removeItem('askMate_Password');
    deleteUser(user);
    navigate('/');
}

function Account(){
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        async function initUser(id){
            const user = await fetchUser(id);
            setUser(user);
        }
        const id = localStorage.getItem('askMate_UserId');
        initUser(id);
    }, []);



    return (
        <div>
            <h1>
                Account
            </h1>
            <div>
                <Table>
                    <tr>
                        <td>Name:</td>
                        <td>{user.name}</td>
                        <td>ID:</td>
                        <td>{user.id}</td>
                    </tr>
                </Table>
            </div>
            <div>
                <button onClick={e=> handleDelete(navigate)}>
                    Delete Account
                </button>
                <button onClick={e=> handleLogOut(navigate)}>
                    Log Out
                </button>
            </div>
        </div>
    );
}