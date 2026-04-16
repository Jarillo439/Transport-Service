package com.assignment.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.client.DriverClient;
import com.assignment.client.OrderClient;
import com.assignment.dto.AssignmentRequestDto;
import com.assignment.dto.AssignmentResponseDto;
import com.assignment.model.Assignment;
import com.assignment.respository.Repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpements implements AssignmentService {

	private final Repository repository;
	private final OrderClient orderClient;
	private final DriverClient driverClient;

	// carpeta donde se guardan los archivos
	private static final String Upload_Dir = "uploads/";

	@Override
	public AssignmentResponseDto assignDriver(AssignmentRequestDto request) {
		// Validar orden
		Map<String, Object> order = orderClient.getOrderById(request.getOrderId());
		Object statusObj = order.get("status");
		String orderStatus = String.valueOf(statusObj);

		if (!"CREATED".equals(orderStatus)) {
			throw new RuntimeException("La orden debe estar en estado CREATED para asignar un conductor");
		}

		//Validar que el conductor existe y está activo

		Map<String, Object> driver = driverClient.getDriverById(request.getDriverId());
		Boolean driverActive = (Boolean) driver.get("active");
		System.out.println("DRIVER RECIBIDO: " + driver);
		if (Boolean.FALSE.equals(driverActive)) {
			throw new RuntimeException("El conductor debe estar activo para ser asignado");
		}

		// Guardar la asignación
		Assignment assignment = new Assignment();
		assignment.setOrderId(request.getOrderId());
		assignment.setDriverId(request.getDriverId());

		Assignment saved = repository.save(assignment);
		return mapToResponse(saved);

	}

	@Override
	public AssignmentResponseDto uploadFile(UUID assignmentId, MultipartFile file) {
		Assignment assignment = repository.findById(assignmentId)
				.orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

		// validar que sea PDF
		String contentType = file.getContentType();
		if (!"application/pdf".equals(contentType)) {
			throw new RuntimeException("Solo se permiten archivos PDF");
		}
		String filePath = saveFile(file, assignmentId + "_doc.pdf");
		assignment.setFilePath(filePath);
		return mapToResponse(repository.save(assignment));
	}

	@Override
	public AssignmentResponseDto uploadImage(UUID assignmentId, MultipartFile image) {
		Assignment assignment = repository.findById(assignmentId)
	            .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

	    String contentType = image.getContentType();
	    System.out.println("CONTENT TYPE: " + contentType);

	    if (contentType == null) {
	        throw new RuntimeException("No se pudo determinar el tipo de archivo");
	    }
	    boolean isValid = contentType.equals("image/png") 
	                   || contentType.equals("image/jpeg") 
	                   || contentType.equals("image/jpg");
	    System.out.println(isValid);
	    if (!isValid) {
	        throw new RuntimeException("Solo se permiten imágenes PNG o JPG, recibido: " + contentType);
	    }

	    String extension = contentType.equals("image/png") ? ".png" : ".jpg";
	    String imagePath = saveFile(image, assignmentId + "_img" + extension);
	    assignment.setImagePath(imagePath);
	    return mapToResponse(repository.save(assignment));
	}

	private AssignmentResponseDto mapToResponse(Assignment assignment) {
		AssignmentResponseDto response = new AssignmentResponseDto();
		response.setId(assignment.getId());
		response.setOrderId(assignment.getOrderId());
		response.setDriverId(assignment.getDriverId());
		response.setAssignedAt(assignment.getAssignedAt());
		response.setFilePath(assignment.getFilePath());
		response.setImagePath(assignment.getImagePath());
		return response;
	}

	// Método privado para guardar archivos en disco
	private String saveFile(MultipartFile file, String fileName) {
		try {
			Path uploadPath = Paths.get(Upload_Dir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			Path filePath = uploadPath.resolve(fileName);
			Files.write(filePath, file.getBytes());
			return filePath.toString();
		} catch (IOException e) {
			throw new RuntimeException("Error al guardar el archivo: " + e.getMessage());

		}
	}
}
