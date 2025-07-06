package org.jyb.backend.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jyb.backend.entity.Post;
import org.jyb.backend.entity.User;
import org.jyb.backend.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 게시글(Post)은 로그인한 사용자(User)와 연결되어 있어야 하므로,
// 작성자 정보도 함께 저장되도록 설계
@RestController // @Controller + @ResponseBody =json 반환
@RequestMapping("api/posts") // 기본 URL 경로 설정
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    // 게시글 단 건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.findById(id));
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post,
                                           @AuthenticationPrincipal User user) {
        post.setUser(user);
        return ResponseEntity.ok(postService.save(post));
    }

    // 게시글 수정
    @PostMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
                                           @RequestBody Post post,
                                           @AuthenticationPrincipal UserDetails userDetails) { // 현재 로그인한 사용자 정보
        String username = userDetails.getUsername();
        return ResponseEntity.ok(postService.update(id, post, username));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           @AuthenticationPrincipal User user) {
        postService.deleteById(id, user);
        return ResponseEntity.noContent().build();
    }
}
