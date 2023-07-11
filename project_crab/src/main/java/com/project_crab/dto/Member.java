package com.project_crab.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	private int member_no;
	private String member_id;
	private String member_pw; 
	private String member_name; 
	private String member_email;
	private String member_phone; 
	private String member_address;
}
