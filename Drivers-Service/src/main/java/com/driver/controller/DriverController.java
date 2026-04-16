package com.driver.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.driver.dto.DriverRequesDto;
import com.driver.dto.DriverResponseDto;
import com.driver.security.JwtUtil;
import com.driver.service.DriversService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("api/drivers")
@RequiredArgsConstructor
public class DriverController {
	
	private final DriversService service;
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
	
	@PostMapping
	public ResponseEntity<DriverResponseDto> createDriver(@Valid @RequestBody DriverRequesDto request){
		DriverResponseDto response = service.createDriver(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@GetMapping("/active")
	public ResponseEntity<List<DriverResponseDto>> getActiveDrivers(){
		List<DriverResponseDto> drivers = service.getActiveDrivers();
		return ResponseEntity.ok(drivers);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable UUID id){
		DriverResponseDto response = service.getDriverById(id);
		return ResponseEntity.ok(response);
	}
	

}
