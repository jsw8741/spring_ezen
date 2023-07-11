package com.project_crab.service;

import java.util.List;

import com.project_crab.dto.Manager;
import com.project_crab.dto.Member;
import com.project_crab.dto.Product;

public interface CrabService {
	
	public int getDataCount(String searchKey, String searchValue) throws Exception;
	
	public List<Product> getProductList(String searchKey, String searchValue, int start, int end) throws Exception;
	
	public Manager getManager_login(String id, String pw) throws Exception;
	
	public Member getMember_login(String id, String pw) throws Exception;

	public void insert_product(Product product) throws Exception;
	
	public void insert_mem(Member member) throws Exception;
	
	public Member getMypage(int member_no) throws Exception;
	
	public void updateData(Member mem) throws Exception;
	
	public void deleteMem(int member_no) throws Exception;
}
