package com.yoga_booking_springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga_booking_springboot.domain.Role;
import com.yoga_booking_springboot.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	
	private RoleRepository roleRepository;

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	//Szerepkör keresése id szerint
	@Override
	public Role findById(Long id) {
		return roleRepository.findById(id).get();
	}

	//Összes szerepkör lekérdezése
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
	
	
	
	

}
