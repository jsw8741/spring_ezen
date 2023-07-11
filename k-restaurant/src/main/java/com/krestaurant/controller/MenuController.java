package com.krestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {
	
	// 메뉴 전체 리스트
	@GetMapping(value = "/menu/list")
	public String menuList() {
		return "menu/menuList";
	}
}
