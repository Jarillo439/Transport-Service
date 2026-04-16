package com.assignment.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.dto.AssignmentRequestDto;
import com.assignment.dto.AssignmentResponseDto;
import com.assignment.service.AssignmentService;
import com.assignment.security.JwtUtil;

import org.springframework.http.MediaType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/assignment")
@RequiredArgsConstructor
public class AssignmentController {

	private final AssignmentService service;
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
	public ResponseEntity<AssignmentResponseDto> assignDriver(@Valid @RequestBody AssignmentRequestDto request){
		AssignmentResponseDto response = service.assignDriver(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@PostMapping(value = "/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<AssignmentResponseDto> uploadFile(@PathVariable UUID id, @RequestParam("file") MultipartFile file ){
		AssignmentResponseDto response = service.uploadFile(id, file);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value = "/{id}/image" ,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<AssignmentResponseDto> uploadImage(@PathVariable UUID id, @RequestParam("image") MultipartFile image){
		AssignmentResponseDto response = service.uploadImage(id, image);
		return ResponseEntity.ok(response);
		
	}
	
	
	
}

