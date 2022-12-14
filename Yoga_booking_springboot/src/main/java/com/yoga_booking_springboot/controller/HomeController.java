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

	//Bejelentkez??s
	@PostMapping("/login")
	public Person logIn(@RequestParam String emailAddress , String password) {
		return personServiceImpl.logIn(emailAddress, password);
	}
	
	//Regisztr??ci??
	//A request egy user objektum, a response egy string.
	@PostMapping("/signup")
	public String signUp(@RequestBody User actualUser) {
		return personServiceImpl.signUp(actualUser);
	}
	
	//J??ga oktat??k lek??r??se
	@GetMapping("/showyogatrainers")
	public List<Yogatrainer> showYogaTrainers() {
		
		return personServiceImpl.findAllYogatrainer();
	}
	
	//Oktat?? keres??se id alapj??n
	@PostMapping("/findyogatrainerbyid")
	public Yogatrainer findYogatrainerById(@RequestParam long yogatrainerId) {
		return personServiceImpl.findByIdYogatrainer(yogatrainerId);
	}
	
	//Felhaszn??l?? keres??se id alapj??n
	@PostMapping("/finduserbyid")
	public User findUserById(@RequestParam long userId) {
		return personServiceImpl.findByIdUser(userId);
	}
	
	//Felhaszn??l??k lek??r??se
	@GetMapping("/showusers")
	public List<User> showUsers() {
		
		return personServiceImpl.findAllUser();
	}
	
	//Szerepk??r??k lek??r??se
	@GetMapping("/showroles")
	public List<Role> showRoles() {
		
		return roleServiceImpl.findAll();
	}
	
	//J??ga t??pusok lek??r??se
	@GetMapping("/showyogatypes")
	public List<Yogatype> showYogaTypes() {
		
		return yogatypeServiceImpl.findAll();
	}
	
	//Edz??sek lek??r??se
	@GetMapping("/showtrainings")
	public List<Training> showTrainings() {
		
		return trainingServiceImpl.findAll();
	}
	
	//B??rletek lek??r??se
	@GetMapping("/showyogapasses")
	public List<Yogapass> showYogapasses() {
		
		return yogapassServiceImpl.findAll();
		
		
	}
	
	//Edz??sek keres??se d??tum szerint
	@GetMapping("/findalltrainingbydate")
	public List<Training> findAllTrainingByDate(@RequestParam String date){
		return trainingServiceImpl.findAllTrainingByDate(date);
	}
	
	//Edz??sek keres??se d??tum szerint
	//Csak olyan edz??seket adok vissza, ami m??g nincs tele
	@GetMapping("findtrainingbydate")
	public List<Training> findTrainingByDate(@RequestParam String date){
		return trainingServiceImpl.findTrainingByDate(date);
	}
	
	//Edz??sek keres??se d??tum szerint
	//Csak olyan edz??seket adok vissza, ami m??g nincs tele ??s a felhaszn??l?? m??g nem jelentkezett r??
	@PostMapping("findtrainingbydateafterlogin")
	public List<Training> findTrainingByDateAfterLogin(@RequestParam String date , long userId){
		return trainingServiceImpl.findTrainingByDateAfterLogin(date , userId);
	}
	
	//??j edz??s hozz??ad??sa
	@PostMapping("/addnewtraining")
	public String addNewTraining(@RequestBody Training actualTraining) {
		
		return trainingServiceImpl.addNewTraining(actualTraining);
	}
	
	//J??ga oktat?? edz??seinek lek??r??se
	@GetMapping("/showmytrainings")
	public Set<Training> showMyTrainings(@RequestParam long yogatrainerId){
		return personServiceImpl.showMyTrainings(yogatrainerId);
	}
	
	//Egy m??r megl??v?? edz??s valamely param??tern??nek megv??ltozat??sa
	@PatchMapping("/modifytraining")
	public String modifyTraining(@RequestBody Training actualTraining) {
		return trainingServiceImpl.modifyTraining(actualTraining);
	}
	
	//Edz??s t??rl??se
	@DeleteMapping("/deletetraining")
	public String deleteTraining(@RequestParam long trainingId) {
		return trainingServiceImpl.deleteTraining(trainingId);
	}
	
	//J??ga oktat?? ??ltal oktatott t??pusok lek??r??se
	@GetMapping("/showmyyogatypes")
	public Set<Yogatype> showMyYogatypes(@RequestParam long yogatrainerId){
		return personServiceImpl.showMyYogatypes(yogatrainerId);
	}
	
	//J??ga oktat??hoz ??j j??ga t??pus hozz??ad??sa
	@PostMapping("/addyogatype")
	public String addYogatype(@RequestParam long yogatrainerId , long yogatypeId) {
		return personServiceImpl.addYogatype(yogatrainerId, yogatypeId);
	}
	
	
	//Jelentkez??s egy edz??sre
	@PostMapping("/participatetraining")
	public String participateTraining(@RequestParam long userId , long trainingId) {
		return personServiceImpl.participateTraining(userId, trainingId);
	}
	
	//Edz??sek lek??r??se, amire m??r jelentkezett a felhaszn??l??
	@GetMapping("/showusertrainings")
	public Set<Training> showUserTrainings(@RequestParam long userId){
		return personServiceImpl.showUserTrainings(userId);
	}
	
	//Edz??sek lek??r??se d??tum szerint, amire m??r jelentkezett a felhaszn??l??
	@PostMapping("/showusertrainingsbydate")
	public List<Training> showUserTrainingsByDate(@RequestParam long userId , String date){
		return personServiceImpl.showUserTrainingsByDate(userId, date);
	}
	
	//Jelentkez??s lemond??sa
	@PostMapping("/canceltraining")
	public String cancelTraining(@RequestParam long userId , long trainingId) {
		return personServiceImpl.cancelTraining(userId, trainingId);
	}
	
	//B??rlet v??s??rl??sa
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
