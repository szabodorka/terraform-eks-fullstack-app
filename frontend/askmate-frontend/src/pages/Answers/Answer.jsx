
const Answer = ({id, title, message, postDate}) => {
    return (
        <div key={id}>
            <div className="answerHeader">
                <h2>{title}</h2>
                <p className="postDate">{new Date(postDate).toLocaleString('en-UK', {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric',
                    hour: '2-digit',
                    minute: '2-digit'
                })}</p>
            </div>
            <p>{message}</p>
        </div>
    )
}

export default Answer;