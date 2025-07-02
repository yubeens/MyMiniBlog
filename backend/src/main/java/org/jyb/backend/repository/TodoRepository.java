package org.jyb.backend.repository;

import org.jyb.backend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 특정 유저의 할 일 목록만 조회 (로그인 한 사용자 기준)
    List<Todo> findByUserId(Long userId);
}
