package com.docbus.model.user;

import com.docbus.model.user.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsDTO implements UserDetails {

    private final String email;
    private final String password;
    private final UserStatus status;

    public UserDetailsDTO(String email, String password, UserStatus status) {
        this.email = email;
        this.password = password;
        this.status = status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한을 동적으로 할당할 수 있음, 예시로 ROLE_USER 권한을 부여
        return Collections.singletonList(() -> "ROLE_USER");
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ACTIVE;  // 상태가 ACTIVE인 경우만 활성화된 계정으로 간주
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }
}
