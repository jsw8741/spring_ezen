package com.spring.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.project.dao.DeptDao;
import com.spring.project.dto.Dept;

// 1. service의 역할을하는 클래스, 2. 자동으로 bean이 되어 스프링 컨테이너에 등록된다.
@Service
public class DeptServiceImpl implements DeptService {
	
	@Autowired // 스프링 컨테이너가 의존성을 만들어준다.
	DeptDao deptDao;
	
	@Override
	public List<Dept> selectList() {
		
		return deptDao.selectList();
	}
	
}
