import "./Error.css";

export default function Error ({ errorMessage }) {
    return (
        <div className="error">
            {errorMessage}
        </div>
    )
}