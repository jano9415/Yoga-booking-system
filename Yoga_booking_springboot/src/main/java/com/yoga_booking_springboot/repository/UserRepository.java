package com.yoga_booking_springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yoga_booking_springboot.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	//Felhasználó keresése id szerint
	@Override
	public Optional<User> findById(Long id);
	
	//Összes user lekérdezése.
	public List<User> findAll();
	
	//Keresés email cím és jelszó szerint.
	public User findByEmailAddressAndPassword(String emailAddress, String password);
	
	//Keresés email cím szerint.
	public User findByEmailAddress(String emailAddress);
}
