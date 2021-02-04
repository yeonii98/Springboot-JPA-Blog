package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter,setter
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체 생성자
@Builder //빌더 패턴
//ORM-> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity //User 클래스가 MySQL에 테이블이 자동으로 생성된다.
//@DynamicInsert //insert시에 null인 필드를 제외시켜준다.
public class User {
	
	@Id//Primary key 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
																									//오라클에 연결하면 시퀀스를 사용하고
																									//MySQL을 사용하면 auto_increment가 사용된다.
	private int id; //오라클 = 시퀀스 , 넘버링하는 전략 = auto_increment

	
	@Column(nullable= false,length=30, unique = true)//nullable=false /  아이디가 null값이 되면 안되기에 사용한다.
																//length=30 / 글자가 30자 이상이 될수 없게 설정
	private String username; //아이디
	
	@Column(nullable= false,length=100) // length=100 /비밀번호를 넉넉히 준 이유는 암호화를 사용하기 위함이다.
																  // 123456 => 해쉬(비밀번호 암호화)
	private String password; //비밀번호
	
	@Column(nullable= false,length=50) // 이메일도 null이 되면 안된다.
	private String email;//이메일
	
	// @ColumnDefault("'user'")//문자이기 때문에 홑따옴표 써야됨
	@Enumerated(EnumType.STRING)
	private RoleType role;//  Enum을 쓰는게 좋다. //ADMIN, USER
	                                //  도메인(범위) 설정이 가능하다. (성별이라고 치면 = 남,녀)
	                                // 일종의 범위가 정해지는거시다. (학년이라고 치면 = 1학년 ~ 6학년)
	
	@CreationTimestamp//시간이 자동 입력된다.
	private Timestamp createDate;
}