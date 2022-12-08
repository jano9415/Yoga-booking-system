package com.yoga_booking_springboot.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.sym.Name;

@Entity
public class Yogatype {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;
	
	//Kétoldali kapcsolat a Yogatype és a Training között.
	//A Training tábla tartalmazza az idegen kulcsot. Ő a birtokos.
	//Egy jóga típus objektumnak több edzés objektuma van.
	@JsonIgnore
	@OneToMany(mappedBy = "yogatype")
	private Set<Training> trainings = new HashSet<Training>();
	
	
	//Yoga trainer skill kapcsolótábla létrehozása
	
	//Kapcsolat a Yogatype és a Yogatrainer tábla között.
	//A Yogatrainer osztály a birtokos.
	//Egy jóga típus objektumnak több jóga oktató objektuma van.
	@JsonIgnore
	@ManyToMany(mappedBy = "yogatypes")
	private Set<Yogatrainer> yogatrainers = new HashSet<Yogatrainer>();
	
	public Yogatype() {
		
	}

	public Yogatype(String name, String description, Set<Training> trainings, Set<Yogatrainer> yogatrainers) {
		this.name = name;
		this.description = description;
		this.trainings = trainings;
		this.yogatrainers = yogatrainers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	public Set<Yogatrainer> getYogatrainers() {
		return yogatrainers;
	}

	public void setYogatrainers(Set<Yogatrainer> yogatrainers) {
		this.yogatrainers = yogatrainers;
	}
	
	
	
	
	
	
	
	

}
