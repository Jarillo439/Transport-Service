package com.driver.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DriverRequesDto {
	
	@NotBlank(message = "El nombre no tiene que estar vacio")
	@Size(max = 100, message = "el nombre no debe de tener mas de 100 caracteres")
	private String name;

	@NotBlank(message = "la licencia no debe de estar vacia")
	@Size(max = 50, message = "la licencia no debe de tener mas de 50 caracteres")
	private String licenseNumber;
}
