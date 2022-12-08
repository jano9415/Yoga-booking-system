package com.yoga_booking_springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga_booking_springboot.domain.Yogatype;
import com.yoga_booking_springboot.repository.YogatypeRepository;

@Service
public class YogatypeServiceImpl implements YogatypeService{
	
	private YogatypeRepository yogatypeRepository;

	@Autowired
	public void setYogatypeRepository(YogatypeRepository yogatypeRepository) {
		this.yogatypeRepository = yogatypeRepository;
	}

	//Jóga típus keresése id szerint
	@Override
	public Yogatype findById(Long id) {
		return yogatypeRepository.findById(id).get();
	}

	//Összes jóga típus lekérdezése
	@Override
	public List<Yogatype> findAll() {
		return yogatypeRepository.findAll();
	}
	
	
	
	

}
