package com.docbus.controller;

import com.docbus.model.user.RegisterUserDTO;
import com.docbus.model.user.User;
import com.docbus.service.UserService;
import com.docbus.security.JwtTokenUtil; // JwtTokenUtil을 import
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil; // JwtTokenUtil 주입

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId, @RequestHeader("Authorization") String authorization) {
        try {
            // JWT 토큰에서 사용자 ID 추출
            String token = authorization.replace("Bearer ", "");
            Long authenticatedUserId = jwtTokenUtil.getUserIdFromToken(token);

            // 사용자 ID가 일치하지 않으면 403 Forbidden 반환
            if (!authenticatedUserId.equals(userId)) {
                return ResponseEntity.status(403).body(null);  // 자기 자신만 조회 가능
            }

            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @RequestBody RegisterUserDTO registerUserDTO, @RequestHeader("Authorization") String authorization) {
        try {
            // JWT 토큰에서 사용자 ID 추출
            String token = authorization.replace("Bearer ", "");
            Long authenticatedUserId = jwtTokenUtil.getUserIdFromToken(token);

            // 사용자 ID가 일치하지 않으면 403 Forbidden 반환
            if (!authenticatedUserId.equals(userId)) {
                return ResponseEntity.status(403).body("Forbidden: You can only update your own data");
            }

            userService.updateUser(userId, registerUserDTO);
            return ResponseEntity.ok("User updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

}
