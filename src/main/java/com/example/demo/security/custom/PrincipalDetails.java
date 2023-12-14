package com.example.demo.security.custom;

import com.example.demo.model.user.User;
import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class PrincipalDetails implements UserDetails {

    private Long id;
    private String email;
    private String password;
    private String nickname;
    private String role;

    public PrincipalDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole().name();
    }

    public PrincipalDetails(String id, String email, String role) {
        this.id = Long.valueOf(id);
        this.email = email;
        this.role = role;
    }

    public static PrincipalDetails of(String id, String email, String role) {
        return new PrincipalDetails(id, email, role);
    }

    // 회원 권한 정보 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // Username 으로 회원의 PK 반환
    @Override
    public String getUsername() {
        return String.valueOf(this.id);
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
        return true;
    }
}
