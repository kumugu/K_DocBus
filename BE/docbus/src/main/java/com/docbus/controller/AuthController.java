package com.docbus.controller;

import com.docbus.model.auth.LoginRequest;
import com.docbus.model.user.RegisterUserDTO;
import com.docbus.model.user.RegisterUserResponseDTO;
import com.docbus.model.user.User;
import com.docbus.security.JwtTokenUtil;
import com.docbus.service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    // 생성자에서 주입
    public AuthController(UserDetailsServiceImpl userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // 회원가입
    @PostMapping("/registerUser")
    public ResponseEntity<RegisterUserResponseDTO> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        // 회원가입 로직
        User user = userDetailsService.registerUser(registerUserDTO);

        // 응답 생성
        RegisterUserResponseDTO response = new RegisterUserResponseDTO(
                user.getId(),
                user.getEmail(),
                user.getName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // 로그인 정보 확인 후 사용자 인증
        boolean authenticated = userDetailsService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        if (!authenticated) {
            return ResponseEntity.status(401).body("Authentication failed");
        }

        // JWT 토큰 생성
        User user = userDetailsService.getUserByEmail(loginRequest.getEmail()); // 이메일로 사용자 조회
        String token = jwtTokenUtil.generateToken(user.getEmail(), user.getId());

        // 토큰을 반환
        return ResponseEntity.ok("Bearer " + token);
    }
}
