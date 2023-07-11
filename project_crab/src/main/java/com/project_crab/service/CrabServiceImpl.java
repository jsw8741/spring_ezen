package com.project_crab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project_crab.dao.CrabDao;
import com.project_crab.dto.Manager;
import com.project_crab.dto.Member;
import com.project_crab.dto.Product;

@Service
public class CrabServiceImpl implements CrabService{
	@Autowired
	private CrabDao crabMapper;

	@Override
	public List<Product> getProductList(String searchKey, String searchValue, int start, int end) throws Exception {
		return crabMapper.getProductList(searchKey, searchValue, start, end);
	}

	@Override
	public int getDataCount(String searchKey, String searchValue) throws Exception {
		return crabMapper.getDataCount(searchKey, searchValue);
	}

	@Override
	public Manager getManager_login(String id, String pw) throws Exception {
		return crabMapper.getManager_login(id, pw);
	}

	@Override
	public Member getMember_login(String id, String pw) throws Exception {
		return crabMapper.getMember_login(id, pw);
	}

	@Override
	public void insert_mem(Member member) throws Exception {
		crabMapper.insert_mem(member);
	}

	@Override
	public Member getMypage(int member_no) throws Exception {
		return crabMapper.getMypage(member_no);
	}

	@Override
	public void updateData(Member mem) throws Exception {
		crabMapper.updateData(mem);
		
	}

	@Override
	public void deleteMem(int member_no) throws Exception {
		crabMapper.deleteMem(member_no);		
	}

	@Override
	public void insert_product(Product product) throws Exception {
		crabMapper.insert_product(product);		
	}
	
}
