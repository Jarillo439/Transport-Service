package com.assignment.dto;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class AssignmentResponseDto {
	
	private UUID id;
    private UUID orderId;
    private UUID driverId;
    private LocalDateTime assignedAt;
    private String filePath;
    private String imagePath;

}
