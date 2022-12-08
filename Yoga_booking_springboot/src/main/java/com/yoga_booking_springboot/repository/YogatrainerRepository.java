package com.yoga_booking_springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.yoga_booking_springboot.domain.Yogatrainer;

@Repository
public interface YogatrainerRepository extends CrudRepository<Yogatrainer, Long> {
	
	//Jóga oktató keresése id szerint
	@Override
	public Optional<Yogatrainer> findById(Long id);
	
	//Összes yoga oktató lekérdezése
	public List<Yogatrainer> findAll();
	
	//Keresés email cím és jelszó szerint.
	public Yogatrainer findByEmailAddressAndPassword(String emailAddress, String password);
	
	//Keresés email cím szerint.
	public Yogatrainer findByEmailAddress(String emailAddress);

}
