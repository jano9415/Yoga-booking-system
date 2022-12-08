package com.yoga_booking_springboot.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Yogatrainer extends Person {
	
	private String description;
	
	//Kétoldali kapcsolat a Yogatrainer és a Training között.
	//A Training tábla tartalmazza az idegen kulcsot. Ő a birtokos.
	//Egy jóga oktató objektumnak több edzés objektuma van.
	@JsonIgnore
	@OneToMany(mappedBy = "yogatrainer")
	private Set<Training> trainings = new HashSet<Training>();
	
	//Kétoldali kapcsolat a Yogatrainer és a Role között.
	//Idegen kulcs a Role tábla id attribútumára.
	//Ez az osztály a birtokos. Ez tartalmazza az idegen kulcsot.
	//Egy jóga oktató objektumnak egy szerepkör objektuma van.
	@ManyToOne
	private Role role;
	
	
	
	//Yoga trainer skill kapcsolótábla létrehozása
	
	//Kapcsolat a Yogatrainer és a Yogatype tábla között.
	//Ez az osztály a birtokos.
	//Egy jóga oktató objektumnak több jóga típus objektuma van.
	@ManyToMany
	@JoinTable(
			name = "yoga_trainer_skill",
			joinColumns = {@JoinColumn(name = "yogatrainer_id")},
			inverseJoinColumns = {@JoinColumn(name = "yogatype_id")}
			)
	private Set<Yogatype> yogatypes = new HashSet<Yogatype>();
	
	public Yogatrainer() {
		
	}

	public Yogatrainer(String emailAddress, String password, String firstName, String lastName , String description, Role role,  Set<Training> trainings,
						Set<Yogatype> yogatypes) {
		super(emailAddress, password, firstName, lastName );
		this.role = role;
		this.description = description;
		this.trainings = trainings;
		this.yogatypes = yogatypes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	public Set<Yogatype> getYogatypes() {
		return yogatypes;
	}

	public void setYogatypes(Set<Yogatype> yogatypes) {
		this.yogatypes = yogatypes;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	
	
	
	
	
	

}
