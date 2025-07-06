import { Link } from "react-router-dom";

function HomePage() {
    return (
        <div style={{ padding: "20px" }}>
            <h2>홈페이지</h2>
            <p>환영합니다! 아래 페이지들로 이동해보세요.</p>
            <Link to="/register">회원가입</Link> | <Link to="/login">로그인</Link>
            <ul style={{ lineHeight: "2" }}>
                <li>
                    <Link to="/posts">게시글 목록 보기</Link>
                </li>
                <li>
                    <Link to="/todos">할 일 목록 보기</Link>
                </li>
            </ul>
        </div>
    );
}

export default HomePage;
