package com.driver.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.driver.modelo.Drivers;

public interface Repository extends JpaRepository<Drivers, UUID> {
	
	List<Drivers> findByActiveTrue();

}
