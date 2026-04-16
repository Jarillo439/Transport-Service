package com.assignment;
import com.assignment.client.DriverClient;
import com.assignment.client.OrderClient;
import com.assignment.dto.AssignmentRequestDto;
import com.assignment.dto.AssignmentResponseDto;
import com.assignment.model.Assignment;
import com.assignment.respository.Repository;
import com.assignment.service.AssignmentServiceImpements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceTest {

    @Mock
    private Repository assignmentRepository;

    @Mock
    private OrderClient orderClient;

    @Mock
    private DriverClient driverClient;

    @InjectMocks
    private AssignmentServiceImpements assignmentService;

    private Assignment assignment;
    private AssignmentRequestDto requestDto;
    private Map<String, Object> orderMap;
    private Map<String, Object> driverMap;

    @BeforeEach
    void setUp() {
        UUID orderId = UUID.randomUUID();
        UUID driverId = UUID.randomUUID();

        assignment = new Assignment();
        assignment.setId(UUID.randomUUID());
        assignment.setOrderId(orderId);
        assignment.setDriverId(driverId);

        requestDto = new AssignmentRequestDto();
        requestDto.setOrderId(orderId);
        requestDto.setDriverId(driverId);

        orderMap = new HashMap<>();
        orderMap.put("status", "CREATED");

        driverMap = new HashMap<>();
        driverMap.put("active", true);
    }

    @Test
    void assignDriver_ShouldReturnAssignment_WhenValid() {
        when(orderClient.getOrderById(any(UUID.class))).thenReturn(orderMap);
        when(driverClient.getDriverById(any(UUID.class))).thenReturn(driverMap);
        when(assignmentRepository.save(any(Assignment.class))).thenReturn(assignment);

        AssignmentResponseDto response = assignmentService.assignDriver(requestDto);

        assertNotNull(response);
        assertEquals(assignment.getOrderId(), response.getOrderId());
        verify(assignmentRepository, times(1)).save(any(Assignment.class));
    }

    @Test
    void assignDriver_ShouldThrowException_WhenOrderNotCreated() {
        orderMap.put("status", "IN_TRANSIT");
        when(orderClient.getOrderById(any(UUID.class))).thenReturn(orderMap);

        assertThrows(RuntimeException.class, () ->
            assignmentService.assignDriver(requestDto)
        );
    }

    @Test
    void assignDriver_ShouldThrowException_WhenDriverNotActive() {
        driverMap.put("active", false);
        when(orderClient.getOrderById(any(UUID.class))).thenReturn(orderMap);
        when(driverClient.getDriverById(any(UUID.class))).thenReturn(driverMap);

        assertThrows(RuntimeException.class, () ->
            assignmentService.assignDriver(requestDto)
        );
    }
}