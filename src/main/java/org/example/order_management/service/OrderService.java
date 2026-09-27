package org.example.order_management.service;

import jakarta.persistence.criteria.Order;
import lombok.RequiredArgsConstructor;
import org.example.order_management.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<Order> getOwn() {
        return null;
    }
}
