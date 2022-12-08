package com.yoga_booking_springboot.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yoga_booking_springboot.domain.Person;
import com.yoga_booking_springboot.domain.Role;
import com.yoga_booking_springboot.domain.Training;
import com.yoga_booking_springboot.domain.User;
import com.yoga_booking_springboot.domain.Yogapass;
import com.yoga_booking_springboot.domain.Yogatrainer;
import com.yoga_booking_springboot.domain.Yogatype;
import com.yoga_booking_springboot.service.PersonServiceImpl;
import com.yoga_booking_springboot.service.RoleServiceImpl;
import com.yoga_booking_springboot.service.TrainingServiceImpl;
import com.yoga_booking_springboot.service.YogapassServiceImpl;
import com.yoga_booking_springboot.service.YogatypeServiceImpl;

@RestController
public class HomeController {
	
	
	private PersonServiceImpl personServiceImpl;
	private RoleServiceImpl roleServiceImpl;
	private TrainingServiceImpl trainingServiceImpl;
	private YogatypeServiceImpl yogatypeServiceImpl;
	private YogapassServiceImpl yogapassServiceImpl;

		
	@Autowired
	public void setPersonServiceImpl(PersonServiceImpl personServiceImpl) {
		this.personServiceImpl = personServiceImpl;
	}
	
	
	@Autowired
	public void setRoleServiceImpl(RoleServiceImpl roleServiceImpl) {
		this.roleServiceImpl = roleServiceImpl;
	}


	@Autowired
	public void setTrainingServiceImpl(TrainingServiceImpl trainingServiceImpl) {
		this.trainingServiceImpl = trainingServiceImpl;
	}


	@Autowired
	public void setYogatypeServiceImpl(YogatypeServiceImpl yogatypeServiceImpl) {
		this.yogatypeServiceImpl = yogatypeServiceImpl;
	}

	
	@Autowired
	public void setYogapassServiceImpl(YogapassServiceImpl yogapassServiceImpl) {
		this.yogapassServiceImpl = yogapassServiceImpl;
	}

	//Bejelentkezés
	@PostMapping("/login")
	public Person logIn(@RequestParam String emailAddress , String password) {
		return personServiceImpl.logIn(emailAddress, password);
	}
	
	//Regisztráció
	//A request egy user objektum, a response egy string.
	@PostMapping("/signup")
	public String signUp(@RequestBody User actualUser) {
		return personServiceImpl.signUp(actualUser);
	}
	
	//Jóga oktatók lekérése
	@GetMapping("/showyogatrainers")
	public List<Yogatrainer> showYogaTrainers() {
		
		return personServiceImpl.findAllYogatrainer();
	}
	
	//Oktató keresése id alapján
	@PostMapping("/findyogatrainerbyid")
	public Yogatrainer findYogatrainerById(@RequestParam long yogatrainerId) {
		return personServiceImpl.findByIdYogatrainer(yogatrainerId);
	}
	
	//Felhasználó keresése id alapján
	@PostMapping("/finduserbyid")
	public User findUserById(@RequestParam long userId) {
		return personServiceImpl.findByIdUser(userId);
	}
	
	//Felhasználók lekérése
	@GetMapping("/showusers")
	public List<User> showUsers() {
		
		return personServiceImpl.findAllUser();
	}
	
	//Szerepkörök lekérése
	@GetMapping("/showroles")
	public List<Role> showRoles() {
		
		return roleServiceImpl.findAll();
	}
	
	//Jóga típusok lekérése
	@GetMapping("/showyogatypes")
	public List<Yogatype> showYogaTypes() {
		
		return yogatypeServiceImpl.findAll();
	}
	
	//Edzések lekérése
	@GetMapping("/showtrainings")
	public List<Training> showTrainings() {
		
		return trainingServiceImpl.findAll();
	}
	
	//Bérletek lekérése
	@GetMapping("/showyogapasses")
	public List<Yogapass> showYogapasses() {
		
		return yogapassServiceImpl.findAll();
		
		
	}
	
	//Edzések keresése dátum szerint
	@GetMapping("/findalltrainingbydate")
	public List<Training> findAllTrainingByDate(@RequestParam String date){
		return trainingServiceImpl.findAllTrainingByDate(date);
	}
	
	//Edzések keresése dátum szerint
	//Csak olyan edzéseket adok vissza, ami még nincs tele
	@GetMapping("findtrainingbydate")
	public List<Training> findTrainingByDate(@RequestParam String date){
		return trainingServiceImpl.findTrainingByDate(date);
	}
	
	//Edzések keresése dátum szerint
	//Csak olyan edzéseket adok vissza, ami még nincs tele és a felhasználó még nem jelentkezett rá
	@PostMapping("findtrainingbydateafterlogin")
	public List<Training> findTrainingByDateAfterLogin(@RequestParam String date , long userId){
		return trainingServiceImpl.findTrainingByDateAfterLogin(date , userId);
	}
	
	//Új edzés hozzáadása
	@PostMapping("/addnewtraining")
	public String addNewTraining(@RequestBody Training actualTraining) {
		
		return trainingServiceImpl.addNewTraining(actualTraining);
	}
	
	//Jóga oktató edzéseinek lekérése
	@GetMapping("/showmytrainings")
	public Set<Training> showMyTrainings(@RequestParam long yogatrainerId){
		return personServiceImpl.showMyTrainings(yogatrainerId);
	}
	
	//Egy már meglévő edzés valamely paraméternének megváltozatása
	@PatchMapping("/modifytraining")
	public String modifyTraining(@RequestBody Training actualTraining) {
		return trainingServiceImpl.modifyTraining(actualTraining);
	}
	
	//Edzés törlése
	@DeleteMapping("/deletetraining")
	public String deleteTraining(@RequestParam long trainingId) {
		return trainingServiceImpl.deleteTraining(trainingId);
	}
	
	//Jóga oktató által oktatott típusok lekérése
	@GetMapping("/showmyyogatypes")
	public Set<Yogatype> showMyYogatypes(@RequestParam long yogatrainerId){
		return personServiceImpl.showMyYogatypes(yogatrainerId);
	}
	
	//Jóga oktatóhoz új jóga típus hozzáadása
	@PostMapping("/addyogatype")
	public String addYogatype(@RequestParam long yogatrainerId , long yogatypeId) {
		return personServiceImpl.addYogatype(yogatrainerId, yogatypeId);
	}
	
	
	//Jelentkezés egy edzésre
	@PostMapping("/participatetraining")
	public String participateTraining(@RequestParam long userId , long trainingId) {
		return personServiceImpl.participateTraining(userId, trainingId);
	}
	
	//Edzések lekérése, amire már jelentkezett a felhasználó
	@GetMapping("/showusertrainings")
	public Set<Training> showUserTrainings(@RequestParam long userId){
		return personServiceImpl.showUserTrainings(userId);
	}
	
	//Edzések lekérése dátum szerint, amire már jelentkezett a felhasználó
	@PostMapping("/showusertrainingsbydate")
	public List<Training> showUserTrainingsByDate(@RequestParam long userId , String date){
		return personServiceImpl.showUserTrainingsByDate(userId, date);
	}
	
	//Jelentkezés lemondása
	@PostMapping("/canceltraining")
	public String cancelTraining(@RequestParam long userId , long trainingId) {
		return personServiceImpl.cancelTraining(userId, trainingId);
	}
	
	//Bérlet vásárlása
	@PostMapping("/purchaseyogapass")
	public String purchaseYogaPass(@RequestParam long userId , long passId) {
		return personServiceImpl.purchaseYogaPass(userId, passId);
	}
	

	
	@GetMapping("/proba")
	public String proba(@RequestParam String proba1, String proba2) {
		System.out.println(proba1 + " " + proba2);
		
		return "ok";
	}
	
	

}
