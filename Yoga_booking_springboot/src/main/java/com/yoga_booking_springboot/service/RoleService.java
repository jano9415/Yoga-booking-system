package com.yoga_booking_springboot.service;

import java.util.List;
import java.util.Optional;

import com.yoga_booking_springboot.domain.Role;

public interface RoleService {
	
	//Szerepkör keresése id szerint
	public Role findById(Long id);
	
	//Összes szerepkör lekérdezése
	public List<Role> findAll();

}
