package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;
//JpaRepository:User테이블이 관리하는 Repository고 User 테이블의 pk는 integer
//JpaRepository의 findAll : User테이블의 모든 행을 다 리턴
//자동으로 bean등록이 된다.
//@Repository 생략 가능
public interface UserRepository extends JpaRepository<User, Integer> {
	//SELECT * FROM user WHERE username = 1?;
	Optional<User> findByUsername(String username);
}
//JPA Naming 쿼리
//SELECT * FROM user WHERE username = ? AND password = ?
//User findByUsernameAndPassword(String username, String password);

//@Query(value="SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//User login(String username, String password);
