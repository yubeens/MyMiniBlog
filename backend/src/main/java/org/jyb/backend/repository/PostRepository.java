package org.jyb.backend.repository;

import org.jyb.backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 특정 유저의 글 목록만 조회 (로그인 한 사용자 기준)
    List<Post> findByUserId(Long userId);
}
