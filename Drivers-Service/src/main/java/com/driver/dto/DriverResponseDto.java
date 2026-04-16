package com.driver.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class DriverResponseDto {
	
	private UUID id;
	private String name;
	private String licenseNumber;
	private boolean active;
	
}
