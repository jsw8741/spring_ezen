package com.krestaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.krestaurant.dto.MenuSearchDto;
import com.krestaurant.entity.Menu;

public interface MenuRepositoryCustom {
	Page<Menu> getAdminMenuPage(MenuSearchDto menuSearchDto, Pageable pageable);
	
}
