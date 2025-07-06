import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api"

function PostCreatePage() {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const token = localStorage.getItem("token");

            await api.post("/posts", { title, content }, {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });

            alert("게시글이 작성되었습니다!");
            navigate("/posts")
        } catch (err) {
            console.error(err);
            alert("게시글 작성에 실패했습니다.");
        }
    };


    return (
        <div style={{ padding: "20px" }}>
            <h2>게시글 작성</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <input
                        type="text"
                        placeholder="제목"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                        style={{ width: "300px", padding: "5px" }}
                    />
                </div>
                <div style={{ marginTop: "10px" }}>
                    <textarea
                        placeholder="내용"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                        rows="5"
                        style={{ width: "300px", padding: "5px" }}
                    />
                </div>
                <button type="submit" style={{ marginTop: "10px" }}>작성</button>
            </form>
        </div>
    )
}
export default PostCreatePage;