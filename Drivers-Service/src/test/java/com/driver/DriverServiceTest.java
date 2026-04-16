package com.driver;
import com.driver.dto.DriverRequesDto;
import com.driver.dto.DriverResponseDto;
import com.driver.modelo.Drivers;
import com.driver.repository.Repository;
import com.driver.service.DriversServiceImplement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private Repository driverRepository;

    @InjectMocks
    private DriversServiceImplement driverService;

    private Drivers driver;
    private DriverRequesDto requestDTO;

    @BeforeEach
    void setUp() {
        driver = new Drivers();
        driver.setId(UUID.randomUUID());
        driver.setName("Juan López");
        driver.setLicenseNumber("ABC-123");
        driver.setActive(true);

        requestDTO = new DriverRequesDto();
        requestDTO.setName("Juan López");
        requestDTO.setLicenseNumber("ABC-123");
    }

    @Test
    void createDriver_ShouldReturnDriverResponse() {
        when(driverRepository.save(any(Drivers.class))).thenReturn(driver);

        DriverResponseDto response = driverService.createDriver(requestDTO);

        assertNotNull(response);
        assertEquals("Juan López", response.getName());
        assertEquals("ABC-123", response.getLicenseNumber());
        assertTrue(response.isActive());
        verify(driverRepository, times(1)).save(any(Drivers.class));
    }

    @Test
    void getActiveDrivers_ShouldReturnListOfActiveDrivers() {
        when(driverRepository.findByActiveTrue()).thenReturn(List.of(driver));

        List<DriverResponseDto> drivers = driverService.getActiveDrivers();

        assertNotNull(drivers);
        assertEquals(1, drivers.size());
        assertTrue(drivers.get(0).isActive());
        verify(driverRepository, times(1)).findByActiveTrue();
    }

    @Test
    void createDriver_ShouldSetActiveToTrue() {
        when(driverRepository.save(any(Drivers.class))).thenReturn(driver);

        DriverResponseDto response = driverService.createDriver(requestDTO);

        assertTrue(response.isActive());
    }
}