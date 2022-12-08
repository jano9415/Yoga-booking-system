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
public class Role {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String roleName;
	
	//Kétoldali kapcsolat a Role és a User között.
	//A User tábla tartalmazza az idegen kulcsot. Ő a birtokos.
	//Egy szerepkör objektumnak több felhasználó objektuma van.
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private Set<User> users = new HashSet<User>();
	
	//Kétoldali kapcsolat a Role és a Yogatrainer között.
	//A Yogatrainer tábla tartalmazza az idegen kulcsot. Ő a birtokos.
	//Egy szerepkör objektumnak több jóga oktató objektuma van.
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private Set<Yogatrainer> yogatrainers = new HashSet<Yogatrainer>();
	
	public Role() {
		
	}

	public Role(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public Set<Yogatrainer> getYogatrainers() {
		return yogatrainers;
	}

	public void setYogatrainers(Set<Yogatrainer> yogatrainers) {
		this.yogatrainers = yogatrainers;
	}
	
	
	
	
	
	

}
