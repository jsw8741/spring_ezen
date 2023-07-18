package com.krestaurant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.krestaurant.dto.ReservationDto;

@Controller
public class MainController {

	@GetMapping(value = "/")
	public String index(Model model) {
		ReservationDto reservationDto = new ReservationDto();
		model.addAttribute("reservationDto", reservationDto);
		
		return "index";
	}
	
	@GetMapping(value = "/about")
	public String about() {
		return "about/about";
	}
	
}
