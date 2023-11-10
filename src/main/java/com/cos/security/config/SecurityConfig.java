package com.cos.security.config;

import com.cos.security.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity//활성화 시키는 어노테이션 -> 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다. 시큐리티 필터란 이 시큐리티 컨피그 파일을 말함
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //secured 어노테이션 활성화, preAuthorize,postAuthorize 어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

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
                .defaultSuccessUrl("/")
                //로그인 성공시 "/" 주소를 호출함 그러나 로그아웃 후 다시 /user에서 로그인 성공시 "/"로 가는 게 아니라 "/user"로 보내준다 -> 내가 가려는 페이지로 가준다.
                //결국 이 말은 /loginForm에서 바로 시작시에는 .defaultSuccessUrl("/") 이 메서드로 설정된 주소로 보내주고
                //어떤 특정페이지를 요청을 해서 로그인 하게 되면 그 페이지를 보내준다
                .and()//구글로그인 시작점
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOauth2UserService);
                //구글 로그인이 완료된 뒤의 후처리가 필요함
                // 1.코드받기(인증), 2.액세스토큰(권한), 3.사용자 프로필 정보를가져오고
                // 4.그 정보를 토대로 회원가입을 자동으로 진행시키기도 함. 4-2(이메일, 전화번호, 이름, 아이디)
                //ex)쇼핑몰 -> 집주소 이럴 경우 추가적인 정보를 얻을 수 있는 회원가입 페이지를 추가로 만들어야함.
                //구글 로그인이 완료되면 코드X, (액세스 토큰 + 사용자 프로필 정보O)
    }
}
