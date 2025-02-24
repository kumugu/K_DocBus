package com.docbus.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final String SECRET_KEY = "gumkey";

    // 토큰 생성 시 사용자 ID를 포함
    public String generateToken(String username, Long userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)  // 사용자 ID를 클레임에 포함
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))  // 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // 토큰에서 사용자 이름 추출
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 토큰에서 사용자 ID 추출
    public Long extractUserId(String token) {
        return Long.valueOf(extractClaims(token).get("userId", String.class));  // "userId" 클레임에서 사용자 ID 추출
    }

    // Claims (정보) 추출
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 유효성 검증
    public boolean isTokenValid(String token) {
        return !extractClaims(token).getExpiration().before(new Date());
    }

    // HTTP 요청에서 토큰 추출
    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // "Bearer "를 제외한 토큰만 반환
        }
        return null;
    }

    // getUserIdFromToken 메서드: token에서 사용자 ID를 추출
    public Long getUserIdFromToken(String token) {
        return extractUserId(token);  // 이미 존재하는 extractUserId 메서드를 호출
    }
}
