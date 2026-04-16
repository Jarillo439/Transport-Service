package com.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRequestDto {
	
	@NotBlank(message = "El origen no debe estar en blanco")
	@Size(max = 100, message = "el nombre no debe de tener mas de 100 caracteres")
	private String origin;
	
	@NotBlank(message = "El destino no tiene que estar vacio")
	@Size(max = 100, message = "el nombre no debe de tener mas de 100 caracteres")
	private String destination;

}
