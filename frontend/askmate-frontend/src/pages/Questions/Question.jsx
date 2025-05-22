import {useNavigate} from 'react-router-dom';

const Question = ({id, title, description, postDate}) => {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate(`/u/${id}`);
    };
    return <>
        <div className="questionHeader">
            <h2 onClick={() => handleClick(id)}>{title}</h2>
            <p className="questionPostDate">{new Date(postDate).toLocaleString('en-UK', {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
            })}</p>
        </div>
        <p>{description}</p>
    </>
}

export default Question;