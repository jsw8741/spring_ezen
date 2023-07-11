package com.spring.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.project.dto.Dept;
import com.spring.project.service.DeptService;

@RestController
public class DeptController {
	@Autowired
	DeptService deptService;
	
	@GetMapping("/main")
	public List<Dept> main(){
		List<Dept> list = deptService.selectList();
		
		return list;
	}
}
