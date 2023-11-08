package com.cos.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"","/"})
    public String index(){
        
        //머스태치 사용
        //viewResolver 설정 : teplates (prefix), .mustache(suffix) 생략가능
        return "index"; //src/main/resource/templates/index.mustache
    }
}
