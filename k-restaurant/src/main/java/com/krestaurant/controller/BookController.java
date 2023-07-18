package com.krestaurant.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.krestaurant.dto.ReservationDto;
import com.krestaurant.entity.Menu;
import com.krestaurant.service.BookService;
import com.krestaurant.service.MenuService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController {
	private final BookService bookService;
	private final MenuService menuService;
	
	@GetMapping(value = "/bookPage")
	public String bookPage(Model model) {
		ReservationDto reservationDto = new ReservationDto();
		List<Menu> menus = menuService.getMenuList();
		
		model.addAttribute("menus", menus);
		model.addAttribute("reservationDto", reservationDto);		
		
		return "book/bookPage";
	}
	
	@PostMapping(value = "/book/new")
	public String bookForm(Model model, @Valid ReservationDto reservationDto, BindingResult bindingResult,
						   Principal principal){
		
		if(bindingResult.hasErrors()) {
			return "book/bookPage";
		}
		
		String email = principal.getName(); // id에 해당하는 정보를 가지고 온다(email)
		Long bookId;
		
		try {
			bookId = bookService.book(reservationDto, email);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage",  "예약 중 에러가 발생했습니다.");
			return "book/bookPage";
		}
		
		
		return "redirect:/";
	}
}
