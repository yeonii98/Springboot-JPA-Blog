package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
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
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	//auto_increment 사용
	private int id;
	
	@Column(nullable=false,length=100)
	private String title;
	
	@Lob //대용량 데이터
	private String content; //섬머노트 라이브러리 사용 예정<html> 태그가 섞여서 디자인이 됨.
	
	@ColumnDefault("0") //int라서 홑따옴표 필요 없다.
	private int count; //조회수
	
	@ManyToOne	(fetch = FetchType.EAGER)	//Many = Board, One = User  한 명의 User는 여러 개의 게시글을 쓸 수 있따.
	//ManyToOne의 기본전략 : EAGER 타입 : Board테이블을 가져올 때 무조건 가져온다.
	@JoinColumn(name="userId") //userId라는 컬럼 만들어짐
	private User user; //DB는 오브젝트를 저장할 수 없다. FK사용..자바는 오브젝트를 저장할 수 있다.
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedBy : 난 연관관계의 주인이 아니다 (난 FK가 아니에요) DB에 칼럼을 만들지 마세요.
	//OneToMany의 기본전략은 LAZY. 들고와도 되고 안 들고 와도 된다.
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
}
