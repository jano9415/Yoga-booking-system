package com.yoga_booking_springboot.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoga_booking_springboot.domain.Training;

public interface TrainingService {
	
	//Edzés keresése id szerint
	public Training findById(Long id);
	
	//Összes edzés lekérdezése
	public List<Training> findAll();
	
	//Új edzés hozzáadása
	public String addNewTraining(Training actualTraining);
	
	//Egy már meglévő edzés valamely paraméternének megváltozatása
	public String modifyTraining(Training actualTraining);
	
	//Edzés törlése
	public String deleteTraining(long trainingId);
	
	//Edzések keresése dátum szerint
	public List<Training> findAllTrainingByDate(String date);
	
	//Edzések keresése dátum szerint
	//Csak olyan edzéseket adok vissza, ami még nincs tele
	public List<Training> findTrainingByDate(String date);
	
	//Edzések keresése dátum szerint
	//Csak olyan edzéseket adok vissza, ami még nincs tele és a felhasználó még nem jelentkezett rá
	public List<Training> findTrainingByDateAfterLogin(String date , long userId);
	
	

}
