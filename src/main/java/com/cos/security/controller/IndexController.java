package com.cos.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

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
    @GetMapping("/login")
    @ResponseBody
    public String login(){
        return "login";
    }

    @GetMapping("/join")
    @ResponseBody
    public String join(){
        return "join";
    }

    @GetMapping("/joinProc")
    @ResponseBody
    public String joinProc(){

        return "회원가입 완료됨!";
    }
}
