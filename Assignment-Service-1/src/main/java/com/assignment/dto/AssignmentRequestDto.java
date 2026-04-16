package com.assignment.dto;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignmentRequestDto {

	@NotNull(message = "el ID de la orden es obligatorio")
	private UUID orderId;
	
	@NotNull(message = "el ID del conductor es obligatorio")
	private UUID driverId;
}
