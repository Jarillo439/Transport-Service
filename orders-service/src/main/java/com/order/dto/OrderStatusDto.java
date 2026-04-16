package com.order.dto;

import com.order.model.OrderStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusDto {
	
	@NotNull(message = "el estado es obligatorio")
	private OrderStatus status;
	

}
