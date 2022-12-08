package com.yoga_booking_springboot.service;

import java.util.List;

import com.yoga_booking_springboot.domain.Yogapass;

public interface YogapassService {
	
	//Bérlet keresése id szerint
	public Yogapass findById(Long id);
	
	//Összes bérlet lekérdezése
	public List<Yogapass> findAll();

}
