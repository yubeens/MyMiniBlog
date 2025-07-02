package org.jyb.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 할 일 고유 번호 (PK)

    @Column(nullable = false, length = 200)
    private String content; // 할 일 내용 (ex : 공부하기, 운동하기 등)

    private Boolean status = false; // 완료 여부 (default : 미완료)

    private LocalDateTime createdAt = LocalDateTime.now(); // 생성 시점

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 해당 할 일의 소유 사용자
}
