package com.yoga_booking_springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yoga_booking_springboot.domain.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	
	//Szerepkör keresése id szerint
	@Override
	public Optional<Role> findById(Long id);
	
	//Összes szerepkör lekérdezése
	@Override
	public List<Role> findAll();

}
