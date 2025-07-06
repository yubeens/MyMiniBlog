package org.jyb.backend.service;

import org.jyb.backend.entity.User;

public interface UserService {
    User register(User user); // 회원 저장
    User login(String username, String password); // username으로 회원 조회
}

// UserService와 UserServiceImpl
// UserService = 무엇을 할지 정의한 약속, 계약서 같은거
// UserServiceImpl = 그 약속을 어떻게 수행할지 구현한 실제 코드

// 왜 나누냐?
// 도메인이 주요하거나, 로직이 복잡하거나, 다른 구현체로 바꿀 가능성이 있는 경우
// 예: 관리자용 유저 조회, 일반 유저 조회

// 즉
// 로직이 복잡하거나, 확장/대체 가능성 있음 => service + serviceImpl
// 로직이 간단하고 확장 계획 없음 => service 하나면 충분
