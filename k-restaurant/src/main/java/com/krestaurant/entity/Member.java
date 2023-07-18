package com.krestaurant.entity;


import org.springframework.security.crypto.password.PasswordEncoder;

import com.krestaurant.constant.Role;
import com.krestaurant.dto.MemberFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // 엔티티 클래스로 정의
@Table(name="member") // 테이블 이름 지정
@Getter
@Setter
@ToString
public class Member {

	@Id
	@Column(name="member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false, length = 255)
	private String name;

	@Column(unique = true, length = 255)
	private String email;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String phone; 
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
		String password = passwordEncoder.encode(memberFormDto.getPassword());
		
		Member member = new Member();
		member.setName(memberFormDto.getName());
		member.setEmail(memberFormDto.getEmail());
		member.setPassword(password);
		member.setPhone(memberFormDto.getPhone());
		member.setRole(Role.USER);
		
		return member;
	}
}
