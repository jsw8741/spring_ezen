package com.krestaurant.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티 클래스로 정의
@Table(name="qa") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Qa {
	
	@Id
	@Column(name="qa_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String qaText;
	
	private LocalDateTime qaDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
