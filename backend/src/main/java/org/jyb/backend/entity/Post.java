package org.jyb.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 게시글 고유 번호 (PK)

    private String title; // 글 제목

    private String content; // 글 내용 (긴 글도 저장 가능)

    private LocalDateTime createdAt = LocalDateTime.now(); // 글 작성 시간 (default -> 현재시간)

    // 다대일 관계 : 여러 post가 하나의 User에게 속함
    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩 (성능 최적화)
    @JoinColumn(name = "user_id") // 외래키 이름 (DB 칼럼명 : user_id)
    private User user; //글 작성자 (User 객체 참조)


}
