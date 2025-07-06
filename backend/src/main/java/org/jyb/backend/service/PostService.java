package org.jyb.backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jyb.backend.entity.Post;
import org.jyb.backend.entity.User;
import org.jyb.backend.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Lombok이 자동으로 생성자를 만들어줌
// Repository 객체를 생성자 파라미터로 주입
public class PostService {

    private final PostRepository postRepository;

    // 글 작성
    @Transactional
    // 이 메서드 안에서 DB에 변화가 생기면, 성공하면 commit / 실패하면 rollback 하겠다.
    public Post save(Post post) {
        return postRepository.save(post);
    }

    // 글 목록 조회
    public List<Post> findAll() { // 전체 목록 가져오기
        return postRepository.findAll();
    }

    // 특정 글 조회
    public Post findById(long id) {
        // 특정 id의 게시글이 있으면 그 Post 객체 리턴, 없으면 null 리턴
        return postRepository.findById(id).orElse(null);
    }

    // 글 수정
    @Transactional
    public Post update(Long id, Post updatePost, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("작성자만 수정할 수 있습니다.");
        }

        post.setTitle(updatePost.getTitle());
        post.setContent(updatePost.getContent());
        // 저장 후 반환
        return postRepository.save(post);
    }

    // 글 삭제
    @Transactional // 있으면 지우고 없으면 rollback
    public void deleteById(long id, User user) {
        Post post = postRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("권한 없음");
        }
        postRepository.delete(post);
    }

    // 특정 유저의 글 목록 (Entity User 기준임 user_id => jpa가 자동으로 userId로 매핑 (단 이름은 같아야 함))
    public List<Post> findByUserId(long userId) {
        return postRepository.findByUserId(userId);
    }
}
