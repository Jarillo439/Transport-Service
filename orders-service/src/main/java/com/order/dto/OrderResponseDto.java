package com.order.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.order.model.OrderStatus;

import lombok.Data;

@Data
public class OrderResponseDto {
	
	private UUID id;
	private OrderStatus status;
	private String origin;
	private String destination;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	
	
}
