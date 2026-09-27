package org.example.order_management.repository;

import org.example.order_management.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByCustomerId(Long customerId);
    List<CustomerOrder> findByCustomer_KeycloakId(String customerKeycloakId);
}
