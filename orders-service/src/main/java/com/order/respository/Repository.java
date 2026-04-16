package com.order.respository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.order.model.Order;

public interface Repository extends JpaRepository<Order, UUID> {

}
