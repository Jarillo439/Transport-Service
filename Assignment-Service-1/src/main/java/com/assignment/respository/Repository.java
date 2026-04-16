package com.assignment.respository;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.model.Assignment;

public interface Repository extends JpaRepository<Assignment, UUID> {

}
