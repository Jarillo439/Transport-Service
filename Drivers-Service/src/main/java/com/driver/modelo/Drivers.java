package com.driver.modelo;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Driver")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Drivers {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name="id" , updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "license_Number", nullable = false, unique = true, length = 50)
	private String licenseNumber;

	@Column(name= "active", nullable = false)
	private boolean active;

}
