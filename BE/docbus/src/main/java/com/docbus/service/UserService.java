package com.docbus.service;

import com.docbus.model.user.LoginDTO;
import com.docbus.model.user.RegisterUserDTO;
import com.docbus.model.user.User;
import com.docbus.model.user.UserStatus;
import com.docbus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public User registerUser(RegisterUserDTO registerUserDTO) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(registerUserDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // 닉네임 중복 체크
        if (userRepository.findByNickname(registerUserDTO.getNickname()).isPresent()) {
            throw new IllegalArgumentException("Nickname already exists");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(registerUserDTO.getPassword());

        // 새로운 사용자 생성
        User user = new User();
        user.setEmail(registerUserDTO.getEmail());
        user.setPassword(encodedPassword); // 암호화된 비밀번호 저장
        user.setName(registerUserDTO.getName());
        user.setNickname(registerUserDTO.getNickname());
        user.setProfileImage(registerUserDTO.getProfileImage());
        user.setStatus(UserStatus.ACTIVE); // 기본적으로 ACTIVE 상태로 설정

        return userRepository.save(user); // DB에 저장
    }

    /**
     * 로그인 메소드 (LoginDTO 사용)
     */
    public String login(LoginDTO loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with this email does not exist"));

        // 비밀번호 확인
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        // JWT 토큰 생성 후 반환
        return generateJwtToken(user);
    }

    private String generateJwtToken(User user) {
        // 실제 JWT 생성 로직은 별도의 라이브러리를 사용하여 구현
        return "generated-jwt-token-for-" + user.getEmail();  // 예시용
    }

    /**
     * 사용자 정보 조회
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    /**
     * 사용자 정보 수정
     */
    public User updateUser(Long userId, RegisterUserDTO updateUserDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // 중복된 이메일과 닉네임 체크
        if (userRepository.findByEmail(updateUserDTO.getEmail()).isPresent() &&
                !existingUser.getEmail().equals(updateUserDTO.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (userRepository.findByNickname(updateUserDTO.getNickname()).isPresent() &&
                !existingUser.getNickname().equals(updateUserDTO.getNickname())) {
            throw new IllegalArgumentException("Nickname already exists");
        }

        // 수정된 정보로 기존 사용자 업데이트
        existingUser.setEmail(updateUserDTO.getEmail());
        existingUser.setName(updateUserDTO.getName());
        existingUser.setNickname(updateUserDTO.getNickname());
        existingUser.setProfileImage(updateUserDTO.getProfileImage());

        // 비밀번호는 암호화해서 수정 (비밀번호가 변경된 경우에만)
        if (updateUserDTO.getPassword() != null && !updateUserDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
        }

        return userRepository.save(existingUser); // 수정된 사용자 저장
    }

}
