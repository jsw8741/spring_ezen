package com.spring.project.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.project.dto.Dept;

@Mapper
public interface DeptDao {
	public List<Dept> selectList();
}
