import {useNavigate} from 'react-router-dom';

const Question = ({id, title, description}) => {
    const navigate = useNavigate();

    const handleClick = () => {
        navigate(`/u/${id}`);
    };
    return <>
        <h2 onClick={() => handleClick(id)}>{title}</h2>
        <p>{description}</p>
    </>
}
export default Question;