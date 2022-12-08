package com.yoga_booking_springboot.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.web.bind.annotation.RequestParam;

import com.yoga_booking_springboot.domain.Person;
import com.yoga_booking_springboot.domain.Training;
import com.yoga_booking_springboot.domain.User;
import com.yoga_booking_springboot.domain.Yogatrainer;
import com.yoga_booking_springboot.domain.Yogatype;

public interface PersonService {
	
	//User keresése id szerint
	public User findByIdUser(Long id);
	
	//Jóga oktató keresése id szerint
	public Yogatrainer findByIdYogatrainer(Long id);
	
	//Összes user lekérdezése.
	public List<User> findAllUser();
	
	//Összes yoga oktató lekérdezése.
	public List<Yogatrainer> findAllYogatrainer();
	
	//User keresése email cím és jelszó szerint.
	public User findByEmailAddressAndPasswordUser(String emailAddress, String password);
	
	//Yoga oktató keresése email cím és jelszó szerint.
	public Yogatrainer findByEmailAddressAndPasswordYogatrainer(String emailAddress, String password);
	
	//Bejelentkezés
	public Person logIn(String emailAddress , String password);
	
	//User mentése az adatbázisba.
	public void saveUser(User actualUser);
	
	//Yoga oktató mentése az adatbázisba.
	public void saveYogatrainer(Yogatrainer actualYogatrainer);
	
	//Regisztráció
	public String signUp(User actualUser);
	
	//User keresése email cím szerint.
	public User findByEmailAddressUser(String emailAddress);
	
	//Yoga oktató keresése email cím szerint.
	public Yogatrainer findByEmailAddressYogatrainer(String emailAddress);
	
	//Jelentkezés egy edzésre
	public String participateTraining(@RequestParam long userId , long trainingId);
	
	//Jóga oktató edzéseinek lekérése
	public Set<Training> showMyTrainings(long yogatrainerId);
	
	//Jóga oktatóhoz új jóga típus hozzáadása
	public String addYogatype(long yogatrainerId , long yogatypeId);
	
	//Edzések lekérése, amire már jelentkezett a felhasználó
	public Set<Training> showUserTrainings(long userId);
	
	//Edzések lekérése dátum szerint, amire már jelentkezett a felhasználó
	public List<Training> showUserTrainingsByDate(long userId , String date);
	
	//Jelentkezés lemondása
	public String cancelTraining(long userId , long trainingId);
	
	//Bérlet vásárlása
	public String purchaseYogaPass(long userId , long passId);
	
	//Jóga oktató által oktatott típusok lekérése
	public Set<Yogatype> showMyYogatypes(long yogatrainerId);

}
