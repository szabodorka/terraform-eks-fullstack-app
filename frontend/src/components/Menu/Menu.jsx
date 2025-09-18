import {Link, Outlet} from "react-router-dom";
import {useState} from "react";
import "./Menu.css"

const Menu = () => {
    const userId = localStorage.getItem("askMate_UserId");
    const [searchTerm, setSearchTerm] = useState("");

    return (
        <>
            <div className="menu">
                <nav className="nav">
                    <Link to={`/u/questions`}><div className="site-title">I Have a Question</div></Link>
                    <div className="nav-buttons">
                        <Link to={`/u/questions`}>
                            {userId ? <button>Questions</button> : <button disabled>Questions</button>}
                        </Link>
                        <input
                            className="search-input"
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
            <Outlet />
        </>
    );
};

export default Menu;
