package com.docbus.service;

import com.docbus.model.user.RegisterUserDTO;
import com.docbus.model.user.User;
import com.docbus.repository.UserRepository;
import com.docbus.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // 회원가입 메서드
    public User registerUser(RegisterUserDTO registerUserDTO) {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(registerUserDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(registerUserDTO.getPassword());

        // 새로운 사용자 생성
        User user = new User();
        user.setEmail(registerUserDTO.getEmail());
        user.setName(registerUserDTO.getName());
        user.setPassword(encodedPassword);

        // 사용자 저장
        userRepository.save(user);

        return user;
    }

    // 로그인 인증 메서드
    public boolean authenticate(String email, String password) {
        // 이메일로 사용자 조회
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return false; // 사용자가 존재하지 않으면 인증 실패
        }

        // 저장된 비밀번호와 입력된 비밀번호 비교
        return passwordEncoder.matches(password, user.getPassword());
    }

    // Spring Security의 UserDetailsService를 구현하는 메서드
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 이메일(username)로 사용자 조회
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Spring Security의 UserDetails 객체 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER") // 기본적으로 ROLE_USER 부여 (권한 관리 필요하면 변경 가능)
                .build();
    }
}
