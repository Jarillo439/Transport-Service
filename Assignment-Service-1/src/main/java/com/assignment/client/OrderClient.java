package com.assignment.client;

import java.util.Map;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "orders-service" , url = "${services.orders.url}")
@FeignClient(name = "orders-service" , url = "http://localhost:8081")
public interface OrderClient {

	@GetMapping("api/order/{id}")
	Map<String, Object> getOrderById(@PathVariable UUID id);
}
