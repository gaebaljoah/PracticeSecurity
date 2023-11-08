package com.cos.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity//활성화 시키는 어노테이션 -> 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
// 시큐리티 필터란 이 시큐리티 컨피그 파일을 말함
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()//위 주소가 아닌 곳을 매핑하면 로그인 페이지로 이동하게됨
                .and()
                .formLogin()
                .loginPage("/login");
    }
}
