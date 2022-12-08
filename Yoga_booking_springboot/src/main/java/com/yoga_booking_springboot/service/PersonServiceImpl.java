package com.yoga_booking_springboot.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yoga_booking_springboot.domain.Person;
import com.yoga_booking_springboot.domain.Training;
import com.yoga_booking_springboot.domain.User;
import com.yoga_booking_springboot.domain.Yogapass;
import com.yoga_booking_springboot.domain.Yogatrainer;
import com.yoga_booking_springboot.domain.Yogatype;
import com.yoga_booking_springboot.repository.UserRepository;
import com.yoga_booking_springboot.repository.YogatrainerRepository;

@Service
public class PersonServiceImpl implements PersonService {
	
	private UserRepository userRepository;
	
	private YogatrainerRepository yogatrainerRepository;
	
	private TrainingServiceImpl trainingServiceImpl;
	
	private YogatypeServiceImpl yogatypeServiceImpl;
	
	private YogapassServiceImpl yogapassServiceImpl;
	
	private RoleServiceImpl roleServiceImpl;

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setYogatrainerRepository(YogatrainerRepository yogatrainerRepository) {
		this.yogatrainerRepository = yogatrainerRepository;
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
	
	@Autowired
	public void setRoleServiceImpl(RoleServiceImpl roleServiceImpl) {
		this.roleServiceImpl = roleServiceImpl;
	}

	//Összes user lekérdezése.
	@Override
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

	//Összes jóga oktató lekérdezése.
	@Override
	public List<Yogatrainer> findAllYogatrainer() {
		return yogatrainerRepository.findAll();
	}

	//User keresés email cím és jelszó szerint.
	@Override
	public User findByEmailAddressAndPasswordUser(String emailAddress, String password) {
		return userRepository.findByEmailAddressAndPassword(emailAddress, password);
	}

	//Yoga oktató keresése email cím és jelszó szerint.
	@Override
	public Yogatrainer findByEmailAddressAndPasswordYogatrainer(String emailAddress, String password) {
		return yogatrainerRepository.findByEmailAddressAndPassword(emailAddress, password);
	}

	
	//Bejelentkezés
	public Person logIn(String emailAddress , String password) {
		
		User actualUser = userRepository.findByEmailAddressAndPassword(emailAddress, password);
		Yogatrainer actualYogatrainer;
		
		if(actualUser != null) {
			return actualUser;
		}
		
		actualYogatrainer = yogatrainerRepository.findByEmailAddressAndPassword(emailAddress, password);
		
		if(actualYogatrainer != null) {
			return actualYogatrainer;
		}
		
		return null;
		
	}


	//User mentése az adatbázisba.
	@Override
	public void saveUser(User actualUser) {
		userRepository.save(actualUser);
		
	}

	//Yoga oktató mentése az adatbázisba.
	@Override
	public void saveYogatrainer(Yogatrainer actualYogatrainer) {
		yogatrainerRepository.save(actualYogatrainer);
		
	}

	//Regisztráció
	@Override
	public String signUp(User actualUser) {
		
		User user = findByEmailAddressUser(actualUser.getEmailAddress());
		long roleId = 1;
		
		if(user != null) {
			return "emailAddressAlredyExist";
		}
		
		actualUser.setRole(roleServiceImpl.findById(roleId));
		actualUser.setYogapass(null);
		actualUser.setOccasionCounter(0);
		userRepository.save(actualUser);
		return "successRegistration";
	}

	//User keresése email cím szerint.
	@Override
	public User findByEmailAddressUser(String emailAddress) {
		return userRepository.findByEmailAddress(emailAddress);
	}

	//Yoga oktató keresése email cím szerint.
	@Override
	public Yogatrainer findByEmailAddressYogatrainer(String emailAddress) {
		return yogatrainerRepository.findByEmailAddress(emailAddress);
	}

	//Jelentkezés egy edzésre
	@Override
	public String participateTraining(long userId, long trainingId) {
		
		int occasionCounter = findByIdUser(userId).getOccasionCounter();
		
		if(occasionCounter >= 1) {
			
			findByIdUser(userId).getTrainings().add(trainingServiceImpl.findById(trainingId));
			trainingServiceImpl.findById(trainingId).getUsers().add(findByIdUser(userId));
			saveUser(findByIdUser(userId));
			
			occasionCounter--;
			findByIdUser(userId).setOccasionCounter(occasionCounter);
			saveUser(findByIdUser(userId));
			
		}
		
		if(occasionCounter == 0) {
			
			findByIdUser(userId).setYogapass(null);
			saveUser(findByIdUser(userId));
		}
		
		
		return "ok";
	}

	//User keresése id szerint
	@Override
	public User findByIdUser(Long id) {
		return userRepository.findById(id).get();
	}

	//Jóga oktató keresése id szerint
	@Override
	public Yogatrainer findByIdYogatrainer(Long id) {
		return yogatrainerRepository.findById(id).get();
	}

	//Jóga oktató edzéseinek lekérése
	@Override
	public Set<Training> showMyTrainings(long yogatrainerId) {
		
		/*Set<Training> trainings = new HashSet<Training>();
		for(Training t : findByIdYogatrainer(yogatrainerId).getTrainings()) {
			t.getYogatrainer().setRole(null);
			t.getYogatrainer().setYogatypes(null);
			trainings.add(t);
		}
		
		return trainings;*/
		
		return findByIdYogatrainer(yogatrainerId).getTrainings();
	}

	//Jóga oktatóhoz új jóga típus hozzáadása
	@Override
	public String addYogatype(long yogatrainerId, long yogatypeId) {
		
		findByIdYogatrainer(yogatrainerId).getYogatypes().add(yogatypeServiceImpl.findById(yogatypeId));
		yogatypeServiceImpl.findById(yogatypeId).getYogatrainers().add(findByIdYogatrainer(yogatrainerId));
		
		saveYogatrainer(findByIdYogatrainer(yogatrainerId));
		return "ok";
	}

	//Edzések lekérése, amire már jelentkezett a felhasználó
	@Override
	public Set<Training> showUserTrainings(long userId) {
		return findByIdUser(userId).getTrainings();
	}
	
	

	//Jelentkezés lemondása
	@Override
	public String cancelTraining(long userId, long trainingId) {
		
		User user = findByIdUser(userId);
		Training training = trainingServiceImpl.findById(trainingId);
		long passId = 1;
		int occassionCounter = user.getOccasionCounter();
		
		if(user.getYogapass() == null) {
			user.setYogapass(yogapassServiceImpl.findById(passId));
			user.setOccasionCounter(yogapassServiceImpl.findById(passId).getOccasionNumber());
		}
		else {
			occassionCounter++;
			user.setOccasionCounter(occassionCounter);
		}
		
		user.getTrainings().remove(training);
		saveUser(user);
		return "ok";
	}

	//Bérlet vásárlása
	@Override
	public String purchaseYogaPass(long userId , long passId) {
		
		findByIdUser(userId).setYogapass(yogapassServiceImpl.findById(passId));
		findByIdUser(userId).setOccasionCounter(yogapassServiceImpl.findById(passId).getOccasionNumber());
		saveUser(findByIdUser(userId));
		return "ok";
	}

	//Jóga oktató által oktatott típusok lekérése
	@Override
	public Set<Yogatype> showMyYogatypes(long yogatrainerId) {
		return findByIdYogatrainer(yogatrainerId).getYogatypes();
	}

	//Edzések lekérése dátum szerint, amire már jelentkezett a felhasználó
	@Override
	public List<Training> showUserTrainingsByDate(long userId, String date) {
		
		Set<Training> trainings = new HashSet<>();
		trainings = findByIdUser(userId).getTrainings();
		
		List<Training> trainingsList = new ArrayList<>();
		
		for(Training t : trainings) {
			if(t.getDate().equals(date)) {
				trainingsList.add(t);
			}
		}
		return trainingsList;
	}
	

	
	
	


	
	
	
	

}
