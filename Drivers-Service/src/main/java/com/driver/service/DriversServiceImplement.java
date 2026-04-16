package com.driver.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.driver.dto.DriverRequesDto;
import com.driver.dto.DriverResponseDto;
import com.driver.modelo.Drivers;
import com.driver.repository.Repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DriversServiceImplement implements DriversService {
	
	private final Repository repository;

	@Override
	public DriverResponseDto createDriver(DriverRequesDto request) {
		Drivers driver = new Drivers();
		driver.setName(request.getName());
		driver.setLicenseNumber(request.getLicenseNumber());
		driver.setActive(true);
		
		Drivers saved = repository.save(driver);
		return mapToResponse(saved);
		
	}

	@Override
	public List<DriverResponseDto> getActiveDrivers() {
		return repository.findByActiveTrue()
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	
	}
	
	private DriverResponseDto mapToResponse(Drivers driver) {
		DriverResponseDto response = new DriverResponseDto();
		response.setId(driver.getId());
		response.setName(driver.getName());
		response.setLicenseNumber(driver.getLicenseNumber());
		response.setActive(driver.isActive());
		return response;
	}

	@Override
	public DriverResponseDto getDriverById(UUID id) {
		Drivers driver = repository.findById(id).orElseThrow(() -> new RuntimeException("Conductor no encontrado con ID: " + id));
		return mapToResponse(driver);
	}

	


}
