package org.jyb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.jyb.backend.entity.Todo;
import org.jyb.backend.entity.User;
import org.jyb.backend.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // json 형태로 응답 보내는 컨트롤러 , @Controller + @ResponseBody = json반환
@RequestMapping("api/todos") // 기본 URL 경로 설정
@RequiredArgsConstructor // final 붙은 생성자 자동 생성 (의존성 주입)
public class TodoController {

    private final TodoService todoService;

    // 전체 Todo 조회
    // ResponseEntity<type> : 응답 코드 + 데이터 반환을 함께 처리
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return ResponseEntity.ok(todoService.findAll());
    }

    // 특정 Todo 조회
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {// URL 경로에서 값을 추출 (예: GET/todos/5(id) => 5를 자동으로 id변수에 넣어줌)
        return ResponseEntity.ok(todoService.findById(id));
    }

    // Todo 생성
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo,
                                           @AuthenticationPrincipal User user) { // RequestBody - json으로 넘어온 값을 객체로 매핑 (JSON 데이터 -> 자바 객체로 변환)
        todo.setUser(user); // 작성자 설정
        return ResponseEntity.ok(todoService.save(todo));
    }

    // Todo 상태 변경 (완료/미완료)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam Boolean status,
                                             @AuthenticationPrincipal User user) { // requestParam : 쿼리파라미터 받기 (/status?status=true)
        todoService.updateStatus(id, status, user);
        return ResponseEntity.ok().build(); // build() : 설정 그대로 실제 ResponseEntity 객체를 만들어 주는 함수 (응답 본문 없이 상태코드만 보냄 (삭제코드 : 204))
    }

    // Todo 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id,@AuthenticationPrincipal User user) {
        todoService.deleteById(id, user);
        return ResponseEntity.noContent().build(); // noContent() : http 상태 코드 204를 의미 (요청은 성공했지만 응답 본문은 없음)
    }

    // ResponsEntity<T> : 제네릭 T는 응답 본문에 담길 타입을 의미 (HTTP응답을 보다 정확하게 조절)
    // ResponsEntity<String> => 본문에 문자열이 들어감
    // ResponsEntity<User> => 본문에 유저 객체(JSON)가 들어감
    // ResponsEntity<Void> => 본문 없음 (대신 상태코드로 의도 전달)

    //@PathVariable : 대상을 고를 때 사용 (어떤 게시물, 어떤 할일?) - URL 경로안 , /todos/{id} 값에서 추출
    //@RequestParam : 부가 조건을 줄 때 사용 (완료, 미완료? / 페이지 몇번째?) - URL 쿼리파라미터


    // GET - 데이터 조회
    // POST - 데이터 생성 (본문:있음, DB 새 데이터 생성)
    // PATCH - 데이터 부분 수정 (본문:수정값만, DB 일부 변경)
    // DELETE - 데이터 삭제 (DB:삭제 발생)

    // [ 브라우저 / Postman / 프론트엔드 ] => Controller(요청 받고 -> 어떤 기능 실행할지 판단)
    // => Service(로직 처리: if문, 조건분기, 예외처리 등) => Repository(DB에 접근해서 데이터 저장/조회) => MySQL DB

}
