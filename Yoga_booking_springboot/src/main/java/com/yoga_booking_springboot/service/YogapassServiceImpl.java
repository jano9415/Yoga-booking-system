package com.yoga_booking_springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga_booking_springboot.domain.Yogapass;
import com.yoga_booking_springboot.repository.YogapassRepository;

@Service
public class YogapassServiceImpl implements YogapassService {
	
	private YogapassRepository yogapassRepository;

	@Autowired
	public void setYogapassRepository(YogapassRepository yogapassRepository) {
		this.yogapassRepository = yogapassRepository;
	}

	//Bérlet keresése id szerint
	@Override
	public Yogapass findById(Long id) {
		return yogapassRepository.findById(id).get();
	}

	//Összes bérlet lekérdezése
	@Override
	public List<Yogapass> findAll() {
		return yogapassRepository.findAll();
	}
	
	
	
	
	


}
