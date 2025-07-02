package org.jyb.backend.service;

import lombok.RequiredArgsConstructor;
import org.jyb.backend.entity.User;
import org.jyb.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // 스프링 서비스 빈 등록
@RequiredArgsConstructor // Lombok이 자동으로 생성자 주입
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; //의존성 주입 대상

    // 실제로는 아래 생성자가 자동 생성됨
    // 스프링이 자동으로 UserRepository 객체 만들어서 UserServiceImpl 안에 넣어줌
    // public UserServiceImpl(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }


    // 회원 저장 (회원가입 시 사용)
    @Override // override : 이 값이 있을 수도 있고, 없을 수도 있다. (회원이 없으면 Optional.empty())
    public User save(User user) {
        return userRepository.save(user);
    }

    // username으로 회원 찾기 (로그인 시 사용)
    @Override
    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null); // 없으면 null 반환 (또는 예외처리)
    }
}
