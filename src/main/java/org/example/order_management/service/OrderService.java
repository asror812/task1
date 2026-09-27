package org.example.order_management.service;

import lombok.RequiredArgsConstructor;
import org.example.order_management.dto.OrderResponseDto;
import org.example.order_management.model.CustomerOrder;
import org.example.order_management.repository.OrderRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOwn(Jwt jwt) {
        return orderRepository.findByCustomer_KeycloakId(jwt.getSubject())
                .stream()
                .map(this::toDto)
                .toList();
    }

    private OrderResponseDto toDto(CustomerOrder order) {
        return new OrderResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getId(),
                order.getWarehouse().getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getDeliveryCity(),
                order.getDeliveryAddress(),
                order.getDeliveryDate()
        );
    }
}
