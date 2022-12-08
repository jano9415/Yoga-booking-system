package com.yoga_booking_springboot.service;

import java.util.List;
import java.util.Optional;

import com.yoga_booking_springboot.domain.Yogatype;

public interface YogatypeService {
	
	//Jóga típus keresése id szerint
	public Yogatype findById(Long id);
	
	//Összes jóga típus lekérdezése
	public List<Yogatype> findAll();

}
