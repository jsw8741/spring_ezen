package com.krestaurant.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.krestaurant.dto.MenuFormDto;
import com.krestaurant.dto.MenuSearchDto;
import com.krestaurant.entity.Menu;
import com.krestaurant.repository.CategoryRepository;
import com.krestaurant.service.MenuService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MenuController {
	private final MenuService menuService;
	private final CategoryRepository categoryRepository;
	
	// 메뉴 전체 리스트
	@GetMapping(value = "/menu/list")
	public String menuList() {
		return "menu/menuList";
	}
	
	// 메뉴 등록 페이지
	@GetMapping(value = "/admin/menu/new")
	public String menuForm(Model model) {
		model.addAttribute("menuFormDto", new MenuFormDto());
		return "menu/menuForm";
	}
	
	// 메뉴, 메뉴 이미지 등록
	@PostMapping(value = "/admin/menu/new")
	public String menuNew(@Valid MenuFormDto menuFormDto, BindingResult bindingResult, Model model, @RequestParam("menuImgFile") List<MultipartFile> menuImgFileList) {
		if(bindingResult.hasErrors()) {
			return "menu/menuForm";
		}
		
		if(menuImgFileList.get(0).isEmpty()) {
			model.addAttribute("errorMessage", "첫번째 메뉴 이미지는 필수 입니다.");
			return "menu/menuForm";
		}
		
		try {			
			menuService.saveMenu(menuFormDto, menuImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage",  "메뉴 등록 중 에러가 발생했습니다.");
			return "menu/menuForm";
		}
		
		return "redirect:/";
	}
	
	// 메뉴 관리 페이지
	@GetMapping(value = {"/admin/menus", "/admin/menus/{page}"})
	public String menuMng(MenuSearchDto menuSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
		Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5); 
		Page<Menu> menus = menuService.getAdminMenuPage(menuSearchDto, pageable);
		
		model.addAttribute("menus", menus);
		model.addAttribute("menuSearchDto", menuSearchDto);
		model.addAttribute("maxPage", 5); 
		
		return "menu/menuMng";
	}
	
	// 메뉴 수정 페이지 화면
	@GetMapping(value = "/admin/menu/{menuId}")
	public String menuDtl(@PathVariable("menuId") Long menuId, Model model) {
		try {
			MenuFormDto menuFormDto = menuService.getMenuDtl(menuId);
			model.addAttribute("menuFormDto", menuFormDto);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage",  "메뉴 수정 페이지를 불러오는 중 에러가 발생했습니다.");
			model.addAttribute("menuFormDto", new MenuFormDto());
			return "menu/menuForm";
		}
		
		return "menu/menuModifyForm";
	}
	
	// 메뉴 수정
	@PostMapping(value = "/admin/menu/{menuId}")
	public String menuUpdate(@Valid MenuFormDto menuFormDto, Model model,
							BindingResult bindingResult, @RequestParam("menuImgFile") List<MultipartFile> menuImgFileList) {
		if(bindingResult.hasErrors()) {
			return "menu/menuForm";
		}
		
		if(menuImgFileList.get(0).isEmpty() && menuFormDto.getId() == null) {
			model.addAttribute("errorMessage", "첫번째 메뉴 이미지는 필수 입니다.");
			return "menu/menuForm";
		}
		
		try {
			menuService.updateMenu(menuFormDto, menuImgFileList);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("errorMessage", "메뉴 수정 중 에러가 발생했습니다.");
			return "menu/menuForm";
		}
		
		return "redirect:/";
	}
	
}
