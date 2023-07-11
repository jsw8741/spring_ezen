package com.krestaurant.entity;

import java.time.LocalDateTime;

import com.krestaurant.constant.BookStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티 클래스로 정의
@Table(name="reservation") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Reservation {
	
	@Id
	@Column(name="book_num")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long num;
	
	private LocalDateTime bookDate;

	@Enumerated(EnumType.STRING)
	private BookStatus bookStatus;
	
	private int bookPersonnel;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_num")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_num")
	private Menu menu;
}
