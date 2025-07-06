import {useState} from "react"; // React의 상태 관리를 위한 Hook, 입력값을 상태로 저장
import {useNavigate} from "react-router-dom"; // 페이지 이동을 위한 Hook, 로그인 성공 시 페이지 이동에 사용
import api from "../api/api"; // Axios로 설정된 API 인스턴스를 import (서버와의 통신 담당)

function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    // 아이디와 비밀번호를 저장하는 상태 변수 두 개 선언

    const navigate = useNavigate();
    // 페이지 이동을 위한 useNavigate 사용

    const handleLogin = async() => {
        try {  // 서버에 로그인 요청 → 응답으로 JWT 토큰 받음
            const res = await api.post('/users/login', {username, password});
            localStorage.setItem('token', res.data.token);  // 받은 토큰을 localStorage에 저장해서, 이후 인증 요청 시 사용
            alert('로그인 성공!')
            navigate('/');  // 로그인 성공 시 게시글 목록 페이지로 이동
        } catch (err) {
            alert('로그인 실패: ' + err.response?.data?.message || err.message);
            // 로그인 실패 시 서버가 준 메시지 또는 일반 오류 메시지를 알림
        }
    };

    return (
        <div>
            <h2>로그인</h2>
            <input placeholder="아이디" value={username} onChange={e => setUsername(e.target.value)} />
            <input placeholder="비밀번호" type="password" value={password} onChange={e => setPassword(e.target.value)} />
            <button onClick={handleLogin}>로그인</button>
        </div>
    )
}
export default LoginPage;