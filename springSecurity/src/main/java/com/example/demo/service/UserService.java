package com.example.demo.service;

import com.example.demo.domain.CustomUserDetails;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String userid, String password) {
        CustomUserDetails user = new CustomUserDetails();
        user.setActualUsername(username);
        user.setUserid(userid);
        user.setPassword(passwordEncoder.encode(password));
        user.setAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")));

        userRepository.save(user);
    }
}