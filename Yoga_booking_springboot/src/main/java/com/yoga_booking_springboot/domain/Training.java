package com.yoga_booking_springboot.domain;


import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;

@Entity
public class Training {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String date;
	
	private String startingTime;
	
	private String finishingTime;
	
	
	private String language;
	
	//Kétoldali kapcsolat a Training és a Yogatype között.
	//Idegen kulcs a Yogatype tábla id attribútumára.
	//Ez az osztály a birtokos. Ez tartalmazza az idegen kulcsot.
	//Egy edzés objektumnak egy jóga típus objektuma van.
	@ManyToOne
	private Yogatype yogatype;
	
	//Kétoldali kapcsolat a Training és a Yogatrainer között.
	//Idegen kulcs a Yogatrainer tábla id attribútumára.
	//Ez az osztály a birtokos. Ez tartalmazza az idegen kulcsot.
	//Egy edzés objektumnak egy jóga oktató objektuma van.
	@ManyToOne
	private Yogatrainer yogatrainer;
	
	private int maxCapacity;
	
	
	
	//Join to training kapcsolótábla létrehozása
	
	//Kapcsolat a Training és a User tábla között.
	//A User osztály a birtokos.
	//Egy edzés objektumnak több felhasználó objektuma van.
	@JsonIgnore
	@ManyToMany(mappedBy = "trainings")
	private Set<User> users = new HashSet<User>();
	
	public Training() {
		
	}

	public Training(String date, String startingTime, String finishingTime, String language, Yogatype yogatype,
			Yogatrainer yogatrainer, int maxCapacity, Set<User> users) {
		this.date = date;
		this.startingTime = startingTime;
		this.finishingTime = finishingTime;
		this.language = language;
		this.yogatype = yogatype;
		this.yogatrainer = yogatrainer;
		this.maxCapacity = maxCapacity;
		this.users = users;
	}
	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartingTime() {
		return startingTime;
	}

	public void setStartingTime(String startingTime) {
		this.startingTime = startingTime;
	}

	public String getFinishingTime() {
		return finishingTime;
	}

	public void setFinishingTime(String finishingTime) {
		this.finishingTime = finishingTime;
	}

	public String getLanguage() {
		return language;
	}
	

	public void setLanguage(String language) {
		this.language = language;
	}

	public Yogatype getYogatype() {
		return yogatype;
	}

	public void setYogatype(Yogatype yogatype) {
		this.yogatype = yogatype;
	}

	public Yogatrainer getYogatrainer() {
		return yogatrainer;
	}

	public void setYogatrainer(Yogatrainer yogatrainer) {
		this.yogatrainer = yogatrainer;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public Long getId() {
		return id;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
	
	
	
	
	

}
