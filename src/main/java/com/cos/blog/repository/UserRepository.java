package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;
//JpaRepository:User테이블이 관리하는 Repository고 User 테이블의 pk는 integer
//JpaRepository의 findAll : User테이블의 모든 행을 다 리턴
//자동으로 bean등록이 된다.
//@Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {

}
