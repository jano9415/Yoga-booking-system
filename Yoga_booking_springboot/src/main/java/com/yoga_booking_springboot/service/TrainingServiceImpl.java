package com.yoga_booking_springboot.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.yoga_booking_springboot.domain.Training;
import com.yoga_booking_springboot.repository.TrainingRepository;

@Service
public class TrainingServiceImpl implements TrainingService {
	
	private TrainingRepository trainingRepository;
	private PersonServiceImpl personServiceImpl;

	@Autowired
	public void setTrainingRepository(TrainingRepository trainingRepository) {
		this.trainingRepository = trainingRepository;
	}
	
	@Lazy
	@Autowired
	public void setPersonServiceImpl(PersonServiceImpl personServiceImpl) {
		this.personServiceImpl = personServiceImpl;
	}



	//Edzés keresése id szerint
	@Override
	public Training findById(Long id) {
		return trainingRepository.findById(id).get();
	}

	//Összes edzés lekérdezése
	@Override
	public List<Training> findAll() {
		return trainingRepository.findAll();
	}

	//Új edzés hozzáadása
	@Override
	public String addNewTraining(Training actualTraining) {
		trainingRepository.save(actualTraining);
		return "ok";
	}

	//Egy már meglévő edzés valamely paraméternének megváltozatása
	@Override
	public String modifyTraining(Training actualTraining) {
		trainingRepository.save(actualTraining);
		
		return "ok";
	}

	//Edzés törlése
	@Override
	public String deleteTraining(long trainingId) {
		trainingRepository.deleteById(trainingId);
		return "ok";
	}

	//Edzés keresése dátum szerint
	@Override
	public List<Training> findTrainingByDateAfterLogin(String date , long userId) {
		List<Training> trainings = new ArrayList<>();
		
		for(Training t : trainingRepository.findByDate(date)) {
			if(t.getMaxCapacity() != t.getUsers().size() && !(t.getUsers().contains(personServiceImpl.findByIdUser(userId)))) {
				trainings.add(t);
			}
		}
		return trainings;
	}

	//Edzések keresése dátum szerint
	//Csak olyan edzéseket adok vissza, ami még nincs tele
	@Override
	public List<Training> findTrainingByDate(String date) {
		List<Training> trainings = new ArrayList<>();
		
		for(Training t : trainingRepository.findByDate(date)) {
			if(t.getMaxCapacity() != t.getUsers().size()) {
				trainings.add(t);
			}
		}
		return trainings;
	}

	//Edzések keresése dátum szerint
	@Override
	public List<Training> findAllTrainingByDate(String date) {
		return trainingRepository.findByDate(date);
	}


	
	
	
	

}
