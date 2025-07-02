package org.jyb.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class User {
    // 기본키(PK), 자동 증가 설정(auto_increment)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 번호 (고유 식별자

    @Column(nullable = false, unique = true)
    private String username;  //사용자 이름 (중복 불가)

    @Column(nullable = false)
    private String password;

}
