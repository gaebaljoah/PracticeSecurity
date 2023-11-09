package com.cos.security.repository;

import com.cos.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//CRUD함수를 JpaRepository
//@Repository라는 어노테이션이 없어도 Ioc 등록이 된다.
public interface UserRepository extends JpaRepository<User, Integer> {

    //findBy까지는 규칙 -> Username은 문법
    //select * from user where username = ? 이런식임
    public User findByUsername(String username);//JPA Query method

    //select * from user where username=? 같은거다.
    //public User findByEmail();
}
