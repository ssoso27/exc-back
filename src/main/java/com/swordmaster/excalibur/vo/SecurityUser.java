package com.swordmaster.excalibur.vo;

import com.swordmaster.excalibur.entity.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

// 인증을 위한 User 객체
@Getter
public class SecurityUser implements UserDetails {
    private String email;
    private String password;
    private ArrayList<SimpleGrantedAuthority> authorities;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    public SecurityUser(Account account) {
        this.email = account.getEmail();
        this.password = account.getPassword();

        this.authorities = new ArrayList<>();
        this.authorities.add(new SimpleGrantedAuthority("user"));
        this.authorities.add(new SimpleGrantedAuthority(account.getRole().getName()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String toString() {
        return "SecurityUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
