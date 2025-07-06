package org.jyb.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 클래스의 모든 필드를 파라미터로 받는 생성자를 자동으로 생성해주는 기능 (DTO나 Entity를 빠르게 만들 때)
public class TokenResponse {
    private String token;
}
