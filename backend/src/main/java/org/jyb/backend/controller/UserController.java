package org.jyb.backend.controller;

import lombok.RequiredArgsConstructor;
import org.jyb.backend.dto.LoginRequest;
import org.jyb.backend.dto.TokenResponse;
import org.jyb.backend.entity.User;
import org.jyb.backend.security.JwtTokenProvider;
import org.jyb.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    // 로그인 -> 유저 검증 후 JWT 토큰 반환
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        // 1. 스프링 시큐리티 인증 수행
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // 2. 인증 객체에서 사용자 정보 꺼냄
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. 토큰 생성
        String token = jwtTokenProvider.createToken(userDetails.getUsername());

        return ResponseEntity.ok(new TokenResponse(token));
    }
}
