package com.docbus.security;

import com.docbus.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 헤더에서 JWT 토큰 추출
        String token = jwtTokenUtil.extractToken(request);

        if (token != null && jwtTokenUtil.isTokenValid(token)) {
            // 토큰이 유효하면 사용자 정보를 가져와서 인증 객체를 설정
            Claims claims = jwtTokenUtil.extractClaims(token);
            String username = claims.getSubject();  // JWT에서 username 추출

            // UserDetailsService를 사용하여 사용자 정보를 로드
            var userDetails = userDetailsService.loadUserByUsername(username);

            // 인증 토큰 생성 (Password는 사용하지 않음)
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // 요청에서 인증 세부 정보 설정
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // SecurityContext에 인증 객체 설정
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // 다음 필터로 요청을 전달
        filterChain.doFilter(request, response);
    }
}
