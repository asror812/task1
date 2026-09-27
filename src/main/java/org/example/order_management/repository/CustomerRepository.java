package org.example.order_management.repository;

import org.example.order_management.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByKeycloakId(String keycloakId);
}
