package org.jyb.backend.security;

import lombok.RequiredArgsConstructor;
import org.jyb.backend.entity.User;
import org.jyb.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService { // Spring Security에서 사용자 인증 시 사용하는 인터페이스

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. DB에서 사용자 조회
        User user = userRepository.findByUsername(username) //DB에서 사용자 조회. 존재하지 않으면 예외 발생.
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + username));

        // 2. UserDetails 반환
        // UserDetails 객체 생성 - Spring Security 에서 사용되는 인증 정용 객체
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())   // 사용자 이름
                .password(user.getPassword())       // 비밀번호 (Spring Security용)
                .authorities("USER")                // 권한 부여 (기본적으로 모든 유저에게 USER 권한)
                .build();
    }
}
    // CustomUserDetailsService는 SpringSecurity 설정 파일(SecurityConfig)에 자동으로 주입
    // -> 로그인 시 loadUserByUsername()을 통해 유저 인증 흐름에 연결됨.