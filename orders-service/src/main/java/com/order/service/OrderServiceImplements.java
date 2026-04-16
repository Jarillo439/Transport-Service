package com.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.order.dto.OrderRequestDto;
import com.order.dto.OrderResponseDto;
import com.order.dto.OrderStatusDto;
import com.order.model.Order;
import com.order.model.OrderStatus;
import com.order.respository.Repository;

@Service

public class OrderServiceImplements implements OrderService {

	private final Repository repository;

	public OrderServiceImplements(Repository repository) {
		this.repository = repository;
	}

	// crear una orden
	@Override
	public OrderResponseDto createOrder(OrderRequestDto request) {
		Order order = new Order();
		order.setOrigin(request.getOrigin());
		order.setDestination(request.getDestination());

		Order saved = repository.save(order);
		return mapToResponse(saved);
	}

	// cambiar estado
	@Override
	public OrderResponseDto changeOrderStatus(UUID id, OrderStatusDto status) {

		Order order = repository.findById(id)
				.orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));

		OrderStatus newStatus = status.getStatus();

		// validar transicion en estados
		if (!isValidStatus(order.getStatus(), newStatus)) {
			throw new RuntimeException("Invalid status transition");
		}
		order.setStatus(newStatus);

		Order updated = repository.save(order);

		return mapToResponse(updated);

	}

	// obtener por id
	@Override
	public OrderResponseDto getOrderById(UUID id) {
		Order order = repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
		return mapToResponse(order);
	}

	// filtros
	@Override
	public List<OrderResponseDto> getOrdersWithFilters(OrderStatus status, String origin, String destination,
			LocalDateTime startDate, LocalDateTime endDate) {
		List<Order> orders = repository.findAll();
		return orders.stream().filter(o -> status == null || o.getStatus() == status)
				.filter(o -> origin == null || o.getOrigin().toLowerCase().contains(origin.toLowerCase()))
				.filter(o -> destination == null
						|| o.getDestination().toLowerCase().contains(destination.toLowerCase()))
				.filter(o -> startDate == null || (o.getCreatedAt() != null && o.getCreatedAt().isAfter(startDate)))

				.filter(o -> endDate == null || (o.getCreatedAt() != null && o.getCreatedAt().isBefore(endDate)))
				.map(this::mapToResponse).toList();
	}

	private OrderResponseDto mapToResponse(Order order) {
		OrderResponseDto response = new OrderResponseDto();
		response.setId(order.getId());
		response.setStatus(order.getStatus());
		response.setOrigin(order.getOrigin());
		response.setDestination(order.getDestination());
		response.setCreatedAt(order.getCreatedAt());
		response.setUpdatedAt(order.getUpdatedAt());

		return response;

	}

	//// Método privado para validar el flujo de estados
	private boolean isValidStatus(OrderStatus current, OrderStatus next) {
		if (current == next)
			return true;

		switch (current) {
		case CREATED:
			return next == OrderStatus.IN_TRANSIT || next == OrderStatus.CANCELLED;

		case IN_TRANSIT:
			return next == OrderStatus.DELIVERED || next == OrderStatus.CANCELLED;

		case CANCELLED:
			return false;

		default:
			return false;
		}

	}
}
