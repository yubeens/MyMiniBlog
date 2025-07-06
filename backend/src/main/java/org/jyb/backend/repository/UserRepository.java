package org.jyb.backend.repository;

import org.jyb.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// User Entity를 DB에서 CRUD할 때 사용하는 인터페이스
public interface UserRepository extends JpaRepository<User, Long> { //<T,ID> => T:entity class, ID:pk type

    // username으로 회원 찾기 (로그인 시 사용)
    Optional<User> findByUsername(String username);

    // 그 외 추가 기능 ( 예 : 이메일로 찾기, 이름 중복 체크 등)
}

// JpaRepository 기능 (메소드 호출만으로 처리 가능)
// save(entity) - 엔티티 저장 (insert or update)
// findById(id) - ID로 조회 (Optional 반환)
// findAll() - 전체조회
// deleteById(id) - ID로 삭제
// delete(entity) - 해당 엔티티 삭제
// count() - 레코드 개수 반환
// existsById(id) - 해당 ID 존재 여부 확인