package com.assignment.client;
import java.util.Map;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "drivers-service", url = "${services.drivers.url}")
@FeignClient(name = "drivers-service", url = "http://localhost:8082")
public interface DriverClient {
	
	@GetMapping("/api/drivers/{id}")
	Map<String, Object> getDriverById(@PathVariable UUID id);
	

}
