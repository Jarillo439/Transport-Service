package com.order;

import com.order.dto.OrderRequestDto;
import com.order.dto.OrderResponseDto;
import com.order.model.Order;
import com.order.model.OrderStatus;
import com.order.respository.Repository;
import com.order.service.OrderServiceImplements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private Repository orderRepository;

    @InjectMocks
    private OrderServiceImplements orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(UUID.randomUUID());
        order.setOrigin("CDMX");
        order.setDestination("Guadalajara");
        order.setStatus(OrderStatus.CREATED);
    }

    @Test
    void createOrder_ShouldReturnOrderResponse() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderRequestDto request = new OrderRequestDto();
        request.setOrigin("CDMX");
        request.setDestination("Guadalajara");

        OrderResponseDto response = orderService.createOrder(request);

        assertNotNull(response);
        assertEquals("CDMX", response.getOrigin());
        assertEquals(OrderStatus.CREATED, response.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void getOrderById_ShouldReturnOrder() {
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.of(order));

        OrderResponseDto response = orderService.getOrderById(order.getId());

        assertNotNull(response);
        assertEquals(order.getId(), response.getId());
    }

    @Test
    void getOrderById_ShouldThrowException_WhenNotFound() {
        when(orderRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> 
            orderService.getOrderById(UUID.randomUUID())
        );
    }
}
