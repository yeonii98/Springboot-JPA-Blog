package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter,setter
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체 생성자
@Builder //빌더 패턴
@Entity //밑에 있는 게 좋당
public class Reply {
	@Id//Primary key 지정
	@GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
																									//오라클에 연결하면 시퀀스를 사용하고
																									//MySQL을 사용하면 auto_increment가 사용된다.
	private int id; //오라클 = 시퀀스 , 넘버링하는 전략 = auto_increment
	
	@Column(nullable=false,length=200)
	private String content;
	
	@ManyToOne //여러개의 답변은 하나의 게시글에 존재할 수 있다.
	@JoinColumn(name="boardId")//필드명은 boardId
	private Board board;
	
	@ManyToOne //여러개의 답변을 하나의 유저가 쓸 수 있다.
	@JoinColumn(name="userId")//필드명은 userId
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
}


