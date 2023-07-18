package com.krestaurant.dto;

import org.modelmapper.ModelMapper;
import com.krestaurant.constant.BookStatus;
import com.krestaurant.entity.Reservation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReservationDto {

	@NotNull(message = "메인 메뉴는 필수 선택입니다.")
	private Long mainMenuId;
	
	@NotBlank(message = "예약자는 필수 입력입니다.")
	private String bookNm;
	
	@NotNull(message = "날짜는 필수 선택입니다.")
	private String bookDate;
	
	private BookStatus bookStatus;
	
	@NotNull(message = "인원수는 필수 입력입니다.")
	private Integer bookPersonnel;
	
	@Min(value = 1, message = "최소 주문 수량은 1개 입니다.")
	@Max(value = 10, message = "최대 주문 수량은 10개 입니다.")
	private Integer count;
	
	private String book_request;
	
	private static ModelMapper modelMapper = new ModelMapper();
	
	//dto -> entity
	public Reservation createReservation() {
		return modelMapper.map(this, Reservation.class);
	}

	//entity -> dto
	public static ReservationDto of(Reservation reservation) {
		return modelMapper.map(reservation, ReservationDto.class);
	}
}
