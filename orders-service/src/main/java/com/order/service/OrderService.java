package com.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.order.dto.OrderRequestDto;
import com.order.dto.OrderResponseDto;
import com.order.dto.OrderStatusDto;
import com.order.model.OrderStatus;

public interface OrderService {
	// crear nueva orden
	OrderResponseDto createOrder(OrderRequestDto request);

	// cambiar el estado de la orden
	OrderResponseDto changeOrderStatus(UUID id, OrderStatusDto status);

	// consultar el orden por id
	OrderResponseDto getOrderById(UUID id);

	// listar ordenes con filtros
	List<OrderResponseDto> getOrdersWithFilters(OrderStatus status, String origin, String destination,
			LocalDateTime startDate, LocalDateTime endDate);

}
