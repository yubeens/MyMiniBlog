package org.jyb.backend.service;

import lombok.RequiredArgsConstructor;
import org.jyb.backend.entity.User;
import org.jyb.backend.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service // 스프링 서비스 빈 등록
@RequiredArgsConstructor // Lombok이 자동으로 생성자 주입
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; //의존성 주입 대상
    private final PasswordEncoder passwordEncoder; //비밀번호 암호화

    // RequiredArgsConstructor => 생성자 자동 생성
    // 실제로는 아래 생성자가 자동 생성됨
    // 스프링이 자동으로 UserRepository 객체 만들어서 UserServiceImpl 안에 넣어줌
    // public UserServiceImpl(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }


    // 회원 저장 (회원가입)
    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // username으로 회원 찾기 (로그인)
    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저 없음"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("비밀번호 불일치");
        }

        return user;
    }
}
