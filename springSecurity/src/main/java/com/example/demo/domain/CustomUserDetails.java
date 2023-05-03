package com.example.demo.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

	//직렬화 가능 클래스 명시적 선언
    private static final long serialVersionUID = 1L;

	private String userid;
 	private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // 생성자, getter, setter 생략

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userid; // 여기서 userid를 반환하도록 합니다.
    }

    public String getUserid() {
        return userid;
    }

    public String getActualUsername() {
        return username;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setActualUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
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
}