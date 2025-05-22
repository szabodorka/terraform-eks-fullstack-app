const Question = ({title, description, postDate}) => {
    return <>
        <div className="questionHeader">
            <h2>{title}</h2>
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
