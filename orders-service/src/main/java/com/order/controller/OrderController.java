package com.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.format.annotation.DateTimeFormat;

import com.order.security.JwtUtil;
import com.order.dto.OrderRequestDto;
import com.order.dto.OrderResponseDto;
import com.order.dto.OrderStatusDto;
import com.order.model.OrderStatus;
import com.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("api/order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;
	private final JwtUtil jwtUtil;
	
	@PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        // validacion simple
        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtUtil.generateToken(username);
            return Map.of("token", token);
        }

        throw new RuntimeException("Credenciales inválidas");
    }

	// crear orden
	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto request) {

		OrderResponseDto response = service.createOrder(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// obtener por id
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID id) {
		OrderResponseDto response = service.getOrderById(id);
		return ResponseEntity.ok(response);
	}

	// cambiar estado
	@PatchMapping("/{id}/status")
	public ResponseEntity<OrderResponseDto> changeStatus(@PathVariable UUID id, @Valid @RequestBody OrderStatusDto status){
		OrderResponseDto response = service.changeOrderStatus(id, status);
		return ResponseEntity.ok(response);
	}

	// listar con filtros
	@GetMapping
	public ResponseEntity<List<OrderResponseDto>> getOrdes(@RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate){
	List<OrderResponseDto> orders = service.getOrdersWithFilters(status, origin, destination, startDate, endDate);
	return ResponseEntity.ok(orders);
		
	}
	
	
	

}
