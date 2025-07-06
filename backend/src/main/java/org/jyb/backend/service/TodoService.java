package org.jyb.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jyb.backend.entity.Todo;
import org.jyb.backend.entity.User;
import org.jyb.backend.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    // 전체 할 일 조회
    public List<Todo> findAll(){
        return todoRepository.findAll();
    }
    // 특정 할 일 조회
    public Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 할일이 존재하지 않습니다."));
    }

    // 할 일 추가
    @Transactional //있으면 commit 없으면 rollback
    public Todo save(Todo todo){
        return todoRepository.save(todo);
    }

    // 유저별 할 일 목록 조회
    public List<Todo> findByUserId(Long userId){
        return todoRepository.findByUserId(userId);
    }

    // 할 일 상태 변경 (예: 완료/미완료 토글)
    // 특정 todo 의 id에 해당하는 할 일을 찾아서, 그 할일의 상태(status)를 주어진 값으로 바꾼 뒤 DB에 저장
    @Transactional // DB 데이터 변경이 일어나기 때문에 중간에 오류나면 자동 롤백
    public void updateStatus(Long todoId, Boolean status, User user){ //todoId: 변경할 할 일의 고유번호(PK)
        //설명
        //Optional(특정 사용자의) 내부에 값이 있으면 -> 그 값 리턴 (Todo 객체)
        //Optional 내부에 값이 없으면 -> new RuntimeException(기본) 발생되며 자동 롤백
        Todo todo = todoRepository.findById(todoId) // orElseThrow() => Optional객체에서 사용되는 메서드로, 해당 값이 존재하지 않을 경우 예외를 던짐.
                .orElseThrow(() -> new RuntimeException("할 일이 존재하지 않습니다."));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("권한이 없습니다.");
        }

        todo.setStatus(status); //상태 값 새로 설정
        todoRepository.save(todo); // DB 저장(JPA가 내부적으로 update sql 실행)


//        //DB에서 해당 id의 todo 찾기 => 리턴값이 Optional인 이유 : 해당 id가 없을 수도 있으니까
//        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
//        if (optionalTodo.isPresent()){ //todo가 존재할 때만 수정 작업 진행 => 없으면 그냥 아무 동작 없이 끝남
//            Todo todo = optionalTodo.get(); //Optional 안에서 실제 Todo 객체 꺼내오기
//            todo.setStatus(status); // 상태값 변경
//            todoRepository.save(todo); // 변경된 객체 다시 DB에 저장
//        }

    }

    // 할 일 삭제
    @Transactional
    public void deleteById(Long id, User user){
        Todo todo = todoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("할 일이 존재하지 않습니다."));

        if(!todo.getUser().getId().equals(user.getId())){
            throw new RuntimeException("권한이 없습니다.");
        }
        todoRepository.delete(todo);
    }

}
