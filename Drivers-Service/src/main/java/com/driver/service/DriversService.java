package com.driver.service;

import java.util.List;
import java.util.UUID;

import com.driver.dto.DriverRequesDto;
import com.driver.dto.DriverResponseDto;

public interface DriversService{
	
	DriverResponseDto createDriver(DriverRequesDto request);
	List<DriverResponseDto> getActiveDrivers();
	DriverResponseDto getDriverById(UUID id);

}
