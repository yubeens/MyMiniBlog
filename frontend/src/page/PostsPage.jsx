import {useEffect, useState} from "react";
// 컴포넌트가 처음 렌더링될 때 API 호출 (useEffect) + 상태 저장용 useState
import {useNavigate} from "react-router-dom";
// 로그인 안 한 사용자는 로그인 페이지로 이동시키기 위해 사용
import api from "../api/api";

function PostsPage() {
    const [posts,setPosts] = useState([]);
    // posts 상태: 게시글 목록 저장

    const navigate = useNavigate(); // 페이지 이동을 위한 navigate 선언

    useEffect(() => {
        const token = localStorage.getItem("token");
        // localStorage에서 JWT 토큰 꺼내오기

        if(!token) {
            alert("로그인이 필요합니다.");
            navigate("/login");
            // 토큰이 없으면 로그인 페이지로 강제 이동
            return;
        }

        // 게시글 불러오기
        api.get("/posts", {
            headers: {
                Authorization:  `Bearer ${token}`,
            },
        })
        .then((res) => {
            setPosts(res.data);  // 성공 시 posts 상태에 게시글 배열 저장

        })
        .catch((err) => {
            alert("게시글을 불러오지 못했습니다.");
            console.error(err); // 에러 발생 시 콘솔 출력
        });

    }, [navigate]); // 페이지가 처음 렌더링될 때 한 번만 실행 (mount 시점)

    return(
        <div>
            <button onClick={() => navigate("/posts/new")}>글쓰기</button>
            <h2>게시글 목록</h2>
            {posts.length === 0 ? (
                <p>게시글이 없습니다.</p>
            ) : (
                <ul>
                    {posts.map((post) => (
                        <li key={post.id}>
                            <h3>{post.title}</h3>
                            <p>{post.content}</p>
                            <p><small>작성자: {post.user.username}</small></p>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}
export default PostsPage;