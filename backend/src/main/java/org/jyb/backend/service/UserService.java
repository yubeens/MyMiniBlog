package org.jyb.backend.service;

import org.jyb.backend.entity.User;

public interface UserService {
    User save(User user); // 회원 저장
    User findByUsername(String username); // username으로 회원 조회
}
