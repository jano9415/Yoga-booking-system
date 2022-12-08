package com.yoga_booking_springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yoga_booking_springboot.domain.Yogapass;

@Repository
public interface YogapassRepository extends CrudRepository<Yogapass, Long> {
	
	//Bérlet keresése id szerint
	@Override
	public Optional<Yogapass> findById(Long id);
	
	//Összes bérlet lekérdezése
	@Override
	public List<Yogapass> findAll();

}
