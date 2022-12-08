package com.yoga_booking_springboot.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Yogapass {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String description;
	
	private int occasionNumber;
	
	private int price;
	
	//Kétoldali kapcsolat a Yogapass és a User között.
	//A User tábla tartalmazza az idegen kulcsot. Ő a birtokos.
	//Egy jóga bérlet objektumnak több felhasználó objektuma van.
	@JsonIgnore
	@OneToMany(mappedBy = "yogapass")
	private Set<User> users = new HashSet<User>();
	
	public Yogapass() {
		
	}

	

	public Yogapass(String description, int occasionNumber, int price, Set<User> users) {
		this.description = description;
		this.occasionNumber = occasionNumber;
		this.price = price;
		this.users = users;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOccasionNumber() {
		return occasionNumber;
	}

	public void setOccasionNumber(int occasionNumber) {
		this.occasionNumber = occasionNumber;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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
