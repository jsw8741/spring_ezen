package com.krestaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krestaurant.entity.Reservation;

public interface BookRepository extends JpaRepository<Reservation, Long>{

}
