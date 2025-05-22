
const Answer = ({id, title, message, postDate}) => {
    return <div key={id}>
        <h2>{title}</h2>
        <p>{message}</p>
        <p className="postDate">{postDate}</p>
    </div>
}

export default Answer;