package com.assignment.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.assignment.dto.AssignmentRequestDto;
import com.assignment.dto.AssignmentResponseDto;


public interface AssignmentService {
	AssignmentResponseDto assignDriver(AssignmentRequestDto request);
	AssignmentResponseDto uploadFile(UUID assignmentId, MultipartFile file);
	AssignmentResponseDto uploadImage(UUID assignmentId, MultipartFile image);

}
