package com.krestaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티 클래스로 정의
@Table(name="menu") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Menu extends BaseEntity{

	@Id
	@Column(name="menu_num")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long num;
	
	@Column(nullable = false)
	private String menuNm;
	
	@Column(nullable = false)
	private int price;
	
	@Lob
	@Column(nullable = false)
	private String menuDetail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_num")
	private Category category;
}
