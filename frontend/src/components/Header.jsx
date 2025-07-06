import { useNavigate } from "react-router-dom";

function Header() {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("token");
        alert("로그아웃 되었습니다.");
        navigate("/login");

    }
    return (
        <header style={{ display: "flex", justifyContent: "space-between", padding: "10px", borderBottom: "1px solid #ccc" }}>
            <h3 style={{ cursor: "pointer" }} onClick={() => navigate("/")}>MyApp</h3>
            <button onClick={handleLogout}>로그아웃</button>
        </header>
    )
}
export default Header;