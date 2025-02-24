package com.docbus.repository;

import com.docbus.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 사용자 조회 (회원가입 시 중복 확인 & 로그인)
    Optional<User> findByEmail(String email);

    // 닉네임으로 사용자 조회 (회원가입 시 중복 확인)
    Optional<User> findByNickname(String nickname);

    boolean existsByEmail(String email);

    User findByEmail(String email);
}


