package com.cos.security.repository;

import com.cos.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//CRUD함수를 JpaRepository
//@Repository라는 어노테이션이 없어도 Ioc 등록이 된다.
public interface UserRepository extends JpaRepository<User, Integer> {
}
