package com.cos.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity//활성화 시키는 어노테이션 -> 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
// 시큐리티 필터란 이 시큐리티 컨피그 파일을 말함
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //해당 메서드의 리턴되는 오브젝트를 ioc로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

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
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌.
                .defaultSuccessUrl("/");
                //로그인 성공시 "/" 주소를 호출함 그러나 로그아웃 후 다시 /user에서 로그인 성공시 "/"로 가는 게 아니라 "/user"로 보내준다 -> 내가 가려는 페이지로 가준다.
                //결국 이 말은 /loginForm에서 바로 시작시에는 .defaultSuccessUrl("/") 이 메서드로 설정된 주소로 보내주고
                //어떤 특정페이지를 요청을 해서 로그인 하게 되면 그 페이지를 보내준다
    }
}
