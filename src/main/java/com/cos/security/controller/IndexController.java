package com.cos.security.controller;

import com.cos.security.model.User;
import com.cos.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"","/"})
    public String index(){

        //머스태치 사용
        //viewResolver 설정 : teplates (prefix), .mustache(suffix) 생략가능
        return "index"; //src/main/resource/templates/index.mustache
    }

    @GetMapping("/user")
    @ResponseBody
    public String user(){
        return "user";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager(){
        return "manager";
    }

    //스프링 시큐리티가 낚아채버림 
    //springConfig 파일 작성 후 작동함
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        user.setRole("ROLE_USER");
        //userRepository.save(user);//회원가입 잘됨.
        // 비밀번호 :1234 => 시큐리티로 로그인을 할 수 없음 이유는 패스워드가 암호화가 안되었기 때문!

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); //인코딩된 패스워드를 넣어준다.

        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")//springConfig에서 설정한 hasRole('ROLE_ADMIN')만 접근 가능 
    @GetMapping("/info")
    public @ResponseBody String info (){
        return "개인정보";
    }
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_MANAGER')") //여러개의 권한 체크시 이 어노테이션 사용 가능 hasRole 문법 사용가능
    @GetMapping("/data")
    public @ResponseBody String data (){
        return "데이터 정보";
    }
}
