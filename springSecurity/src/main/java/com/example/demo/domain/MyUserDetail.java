package com.example.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.stereotype.Service;
import java.util.List;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class MyUserDetail implements UserDetailsService {

    @Autowired
    private DataSource dataSource;

    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM users WHERE userid = ?");
            preparedStatement.setString(1, userid);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new UsernameNotFoundException("User not found");
            }

            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            String role = resultSet.getString("user_role");

            // 여기서 권한을 가져오고 사용자 세부 정보를 구성할 수 있습니다.
            // 예를 들면, authorities 테이블에서 권한을 가져올 수 있습니다.

            CustomUserDetails customUserDetails = new CustomUserDetails();
            customUserDetails.setUserid(userid);
            customUserDetails.setActualUsername(username);
            customUserDetails.setPassword(password);
            customUserDetails.setAuthorities(List.of(new SimpleGrantedAuthority(role)));
            
            return customUserDetails;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 
    }
}



