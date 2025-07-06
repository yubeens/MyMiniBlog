import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api"

function RegisterPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleRegister = async () => {
        try {
            await api.post("/users/register", { username, password });
            alert("회원가입이 완료되었습니다.");
            navigate("/login"); //로그인 페이지로 이동
        } catch (err) {
            alert("회원가입 실패: " + (err.response?.data?.message || err.message));
        }
    };

    return (
        <div>
            <h2>회원가입</h2>
            <input
                type="text"
                placeholder="아이디"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
            />
            <br />
            <input
                type="password"
                placeholder="비밀번호"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <br />
            <button onClick={handleRegister}>가입하기</button>
        </div>
    )
}
export default RegisterPage;