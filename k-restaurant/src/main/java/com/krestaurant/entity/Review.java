package com.krestaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티 클래스로 정의
@Table(name="review") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Review extends BaseEntity{
	
	@Id
	@Column(name="review_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Lob
	@Column(nullable = false)
	private String reviewText;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
}
