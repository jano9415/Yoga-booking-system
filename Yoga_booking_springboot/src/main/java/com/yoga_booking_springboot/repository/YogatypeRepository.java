package com.yoga_booking_springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yoga_booking_springboot.domain.Yogatype;

@Repository
public interface YogatypeRepository extends CrudRepository<Yogatype, Long> {
	
	//Jóga típus keresése id szerint
	@Override
	public Optional<Yogatype> findById(Long id);
	
	//Összes jóga típus lekérdezése
	@Override
	public List<Yogatype> findAll();

}
