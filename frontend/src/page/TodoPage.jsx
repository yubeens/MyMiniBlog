import {useState,useEffect} from "react";
import api from "../api/api";

function TodoPage() {
    const [todos, setTodos] = useState([]);
    // 전체 할 일 목록을 저장하는 상태(배열)
    const [newTodo, setNewTodo] = useState("");
    // 새로 입력한 할 일 내용을 저장하는 상태(문자열)


    // 할 일 불러오기 (GET / api / todos)
    // 1. 로그인 후 받은 토큰을 Authorization 헤더에 담아 보냄.
    // 2. 서버에서 할 일 목록을 받아와 todos 상태에 저장
    // 3. useEffect()에서 페이지 첫 렌더링 시 자동 호출
    const fetchTodos = async () => {
        try {
            const token = localStorage.getItem("token");
            const res = await api.get("/api/todos", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setTodos(res.data);
        }catch (err) {
            alert("할 일을 불러오는데 실패하였습니다.");
            console.error(err);
        }
    };


    // 할 일 추가하기 (POST / api / todos)
    // 1. 사용자가 입력한 새 할 일을 서버에 등록
    // 2. 성공 시 응답으로 받은 새 할 일을 기존 todos 배열에 추가
    // 3. 입력창은 초기화 (setNewTodo(""))
    const addTodo = async () => {
        if (!newTodo.trim()) return;
        try {
            const token = localStorage.getItem("token");
            const res = await api.post(
                "/api/todos",
                {content: newTodo},
                {headers: {Authorization: `Bearer ${token}`}}
            );
            setTodos([...todos, res.data]);
            setNewTodo("");
        } catch (err) {
            alert("할 일을 추가하지 못했습니다.")
        }
    };

    // 할 일 상태 변경 (PATCH / api / todos / {id} / status?status=true|false)
    // 1. 현재 상태를 반전시켜 status 쿼리 파라미터로 서버에 보냄
    // 2. 상태 변경에 성공하면, todos 배열을 새롭게 갱신
    const toggleStatus = async (id, currentStatus) => {
        try {
            const token = localStorage.getItem("token");
            await api.patch(
                `/api/todos/${id}/status?status=${!currentStatus}`,
                {},
                {headers:{Authorization: `Bearer ${token}`}}
            );
            setTodos(
                todos.map((todo) =>
                    todo.id === id ? {...todo, status: !currentStatus}:todo
                )
            );
        } catch (err) {
            alert("상태를 변경하지 못했습니다.")
        }
    };

    // 할 일 삭제 (DELETE / api / todos / {id})
    // 1. 해당 ID의 할 일을 삭제 요청
    // 2. 삭제가 완료되면 해당 항목을 리스트에서 제거
    const deleteTodo = async(id) => {
        try {
            const token = localStorage.getItem("token");
            await api.delete(`/api/todos/${id}`, {
                headers: {Authorization: `Bearer ${token}`},
            });
            setTodos(todos.filter((todo) => todo.id !== id));
        } catch (err) {
            alert("할 일을 삭제하지 못했습니다.")
        }
    };

    // 컴포넌트가 최초로 화면에 렌더링될 때 단 한 번 fechTodos()함수를 호출
    // 즉, TodoPage가 처음 열릴 때 서버로부터 할 일 목록을 가져와서 상태로 저장하라
    useEffect(() => {
        fetchTodos();
    }, []);


    return (
        <div>
            <h2>할 일 목록</h2>
            <input
                placeholder="할 일을 입력하세요"
                value={newTodo}
                onChange={(e) => setNewTodo(e.target.value)}
            />
            <button onClick={addTodo}>추가</button>
            <ul>
                {todos.map((todo) => (
                    <li key={todo.id}>
                        <span
                            style={{
                                textDecoration: todo.status ? "line-through" : "none",
                                cursor: "pointer",
                            }}
                            onClick={() => toggleStatus(todo.id, todo.status)}
                        >
                            {todo.content}
                        </span>
                        <button onClick={() => deleteTodo(todo.id)}>삭제</button>
                    </li>
                ))}
            </ul>
        </div>
    )
}
export default TodoPage;

