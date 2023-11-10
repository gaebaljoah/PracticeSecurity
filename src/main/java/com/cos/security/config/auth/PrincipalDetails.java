package com.cos.security.config.auth;

//시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴
//로그인 진행이 완료되면 시큐리티 session을 만들어줍니다.(Security ContextHolder)
//오브젝트 => Authentication 타입의 객체
//Authentication안에 User 정보가 있어야 됨.
//User 오브젝트 타입 => UserDetails 타입 객체

import com.cos.security.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//Security Session => Authentication => UserDetails(PrincipleDetails).
public class PrincipalDetails implements UserDetails {

    private User user;//콤포지션

    public PrincipalDetails(User user) {
        this.user = user;
    }


    //해당 User의 권한을 리턴하는 곳!!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();

        //이렇게해야 String type을 넣을 수 있다.
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collect;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //패스워드를 가져오는 메서드
    @Override
    public String getPassword() {
        return user.getPassword();
    }


    //user명을 가져오는 메서드
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //당신의 계정이 만료됐는지 묻는 메서드
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠금되었는지 묻는 메서드
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //계정의 비밀번호 유효기간이 지났는지 묻는 메서드
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화되었는지?
    @Override
    public boolean isEnabled() {
        //우리 사이트!! 1년동안 회원이 로그인을 하지 않으면 휴먼계정이된다!
        //이렇게 가장했을 경우 user 모델에 loginData라는 것을 추가하고 그 날짜를 계산해서 메서드의 return 값을 정해준다.
        //위와같이 기획에 따라 모델 필드를 추가하고 그에 맞는 로직을 작성 후 return값을 결정해주면된다
        //ex 로그인 1년 초과 -> false false는 로그인을 거부한다.
        return true;
    }
}
