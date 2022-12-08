package com.yoga_booking_springboot.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Table(name = "yogabookinguser")
@Entity
public class User extends Person {
	
	@Column(nullable = true)
	private int occasionCounter;
	
	//Kétoldali kapcsolat a User és a Yogapass között.
	//Idegen kulcs a Yogapass tábla id attribútumára.
	//Ez az osztály a birtokos. Ez tartalmazza az idegen kulcsot.
	//Egy felhasználó objektumnak egy jóga bérlet objektuma van.
	@ManyToOne
	private Yogapass yogapass;
	
	//Kétoldali kapcsolat a User és a Role között.
	//Idegen kulcs a Role tábla id attribútumára.
	//Ez az osztály a birtokos. Ez tartalmazza az idegen kulcsot.
	//Egy felhasználó objektumnak egy szerepkör objektuma van.
	@ManyToOne
	private Role role;
	
	
	//Join to training kapcsolótábla létrehozása
	
	//Kapcsolat a User és a Training tábla között.
	//Ez az osztály a birtokos.
	//Egy felhasználó objektumnak több edzés objektuma van.
	@ManyToMany
	@JoinTable(
			name = "join_to_training",
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "training_id")}
			)
	private Set<Training> trainings = new HashSet<Training>();
	
	public User() {
	}

	
	public User(String emailAddress, String password, String firstName, String lastName , Yogapass yogapass, Role role,  Set<Training> trainings) {
		super(emailAddress, password, firstName, lastName);
		this.role = role;
		this.yogapass = yogapass;
		this.trainings = trainings;
	}
	
	


	public int getOccasionCounter() {
		return occasionCounter;
	}


	public void setOccasionCounter(int occasionCounter) {
		this.occasionCounter = occasionCounter;
	}


	public Yogapass getYogapass() {
		return yogapass;
	}


	public void setYogapass(Yogapass yogapass) {
		this.yogapass = yogapass;
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}


	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	
	

	
	
	
	







	
	




	
	
}
