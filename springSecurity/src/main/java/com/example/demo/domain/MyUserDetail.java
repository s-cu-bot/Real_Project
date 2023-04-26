package com.example.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                throw new UsernameNotFoundException("User not found");
            }

            String password = resultSet.getString("password");
            String role = resultSet.getString("user_role");

            // 여기서 권한을 가져오고 사용자 세부 정보를 구성할 수 있습니다.
            // 예를 들면, authorities 테이블에서 권한을 가져올 수 있습니다.

            return User.builder()
                    .username(username)
                    .password(password)
                    .roles(role)
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } 
    }
}