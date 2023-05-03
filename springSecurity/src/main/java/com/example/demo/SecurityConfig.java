package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	//WebSecurityAdapter deprecated로 상속 불가 -> SecurityFilterChain으로 대체
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//css와 index 경로에 대한 요청은 모든 사용자가 허용
		//user 경로에 대한 요청은 'USER' 역할 사용자만 허용 (.hasRole("USER"))
		http.csrf().disable().authorizeRequests(authorize -> authorize.requestMatchers("/css/**", "/js/**", "/index").permitAll()
				.requestMatchers("/user/**").hasRole("USER")).formLogin(formLogin -> formLogin.loginPage("/login"));
		return http.build();
	}

	//패스워드 인코더로 BCrypt 알고리즘 사용
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}