package com.cos.security.auth;

import com.cos.security.model.User;
import com.cos.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//시큐리티 설정(SecurityConfig)에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService 타입으로 ioC되어있는 loadUserByUsername() 함수가 실행된다.
//규칙임.
@Service //컨테이너에 등록
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    //시큐리티 session(Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //form에 있는 username 파라미터 등 을 가져온다.
        //findByUsername()함수를 사용하고싶은데 사용할 수 없다 왜냐면 userRepository에 기본적인 CRUD밖에 없기 때문이다.
        //해결하기 위해선 repository에 메서드를 하나 만들어준다.
        System.out.println("username...?"+username);
        User userEntity = userRepository.findByUsername(username);
        System.out.println("user..?"+userEntity);
        if (userEntity!=null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}
