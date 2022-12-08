package com.yoga_booking_springboot.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yoga_booking_springboot.domain.Training;

@Repository
public interface TrainingRepository extends CrudRepository<Training, Long> {
	
	//Edzés keresése id szerint
	@Override
	public Optional<Training> findById(Long id);
	
	//Összes edzés lekérdezése
	@Override
	public List<Training> findAll();
	
	//Edzés keresése dátum szerint
	public List<Training> findByDate(String date);
	


}
