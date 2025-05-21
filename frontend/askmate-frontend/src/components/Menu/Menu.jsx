import { Link } from "react-router-dom";
import {useState} from "react";

const Menu = () => {
    const userId = localStorage.getItem("userId");
    const [searchTerm, setSearchTerm] = useState("");

    return (
        <>
            <div className="menu">
                <nav>
                    <div className="nav-buttons">
                        <input
                            type="text"
                            value={searchTerm}
                            placeholder="Search questions..."
                            onChange={(e) => setSearchTerm(e.target.value)}
                        />
                        <Link to={`/u/search/${searchTerm}`}>
                            {userId && searchTerm ? <button>Search</button> : <button disabled>Search</button>}
                        </Link>
                        <Link to={`/u/create`}>
                            {userId ? <button>Create Question</button> : <button disabled>Create Question</button>}
                        </Link>
                        <Link to={`/u/account`}>
                            {userId ? <button>My Account</button> : <button disabled>My Account</button>}
                        </Link>
                    </div>
                </nav>
            </div>
        </>
    );
};

export default Menu;
