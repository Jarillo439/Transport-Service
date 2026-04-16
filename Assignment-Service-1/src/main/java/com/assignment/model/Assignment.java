package com.assignment.model;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "assignments")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Assignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "order_id", nullable = false)
	private UUID orderId;
	
	@Column(name = "dirver_id", nullable = false)
	private UUID driverId;
	
	@Column(name = "assigned_at", nullable = false)
	private LocalDateTime assignedAt;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@PrePersist
	public void prePersist() {
		this.assignedAt = LocalDateTime.now();
	}

}
