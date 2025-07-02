package org.jyb.backend.repository;

import org.jyb.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// User Entity를 DB에서 CRUD할 때 사용하는 인터페이스
public interface UserRepository extends JpaRepository<User, Long> {

    // username으로 회원 찾기 (로그인 시 사용)
    Optional<User> findByUsername(String username);

    // 그 외 추가 기능 ( 예 : 이메일로 찾기, 이름 중복 체크 등)
}
