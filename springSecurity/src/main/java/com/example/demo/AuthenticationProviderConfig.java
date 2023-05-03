package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.domain.MyUserDetail;

//Security Filter Chain에서 인증 요청을 처리하는 동안 자동 사용 (Authentication Manager)
@Configuration
public class AuthenticationProviderConfig {
    //패스워드 암호화에 사용할 인스턴스, 이거 없으면 passwordEncoder 못씀
    //단순 Encapsulation
    private final PasswordEncoder passwordEncoder;

    //user 로그인 정보
    @Autowired
    private MyUserDetail myUserDetail;
    
    public AuthenticationProviderConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        //사용자 이름과 비밀번호를 기반으로 사용자 인증 처리 (접근제어)
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        //암호화된 패스워드 처리를 위함
        provider.setPasswordEncoder(passwordEncoder);

        //사용자 세부 정보 저장 및 검색
        provider.setUserDetailsService(myUserDetail);

        //인증 성공 시 Authentication 객체 생성하여 인증 사용자 정보와 권한 전달
        return provider;
    }
}