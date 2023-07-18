package com.krestaurant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.krestaurant.dto.ReservationDto;
import com.krestaurant.entity.BookMenu;
import com.krestaurant.entity.Member;
import com.krestaurant.entity.Menu;
import com.krestaurant.entity.Reservation;
import com.krestaurant.repository.BookRepository;
import com.krestaurant.repository.MemberRepository;
import com.krestaurant.repository.MenuImgRepository;
import com.krestaurant.repository.MenuRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
	private final MenuRepository menuRepository;
	private final MenuImgRepository menuImgRepository;
	private final MemberRepository memberRepository;
	private final BookRepository bookRepository;
	
	public Long book(ReservationDto reservationDto, String email) {
		// 1. 예약할 메뉴 조회
		Menu menu = menuRepository.findById(reservationDto.getMainMenuId())
								  .orElseThrow(EntityNotFoundException::new);
		System.out.println("!11111111111111");
		
		// 2. 현재 로그인한 회원 이메일을 이용하여 정보 조회
		Member member = memberRepository.findByEmail(email);
		System.out.println("!222222222222222222222");
		// 3. 예약할 메뉴 엔티티와 예약 수랭을 이용하여 예약 메뉴 엔티티 생성
		List<BookMenu> bookMenuList = new ArrayList<>();
		BookMenu bookMenu = BookMenu.createBookMenu(menu, reservationDto.getCount());
		bookMenuList.add(bookMenu);
		System.out.println("!33333333333333333333");
		// 4. 회원 정보와 예약할 메뉴 리스트 정보를 이용하여 엔티티를 생성
		
		reservationDto.createReservation();
		System.out.println("!444444444444444444");
		Reservation reservation = Reservation.createBook(member, bookMenuList, reservationDto);
		bookRepository.save(reservation);
		System.out.println("!5555555555555555555555");
		return reservation.getId();
	}
	
}
