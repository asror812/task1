package org.example.order_management.dto;

import org.example.order_management.enums.Status;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponseDto(
        Long id,
        Long orderNumber,
        Long customerId,
        Long warehouseId,
        Status status,
        BigDecimal totalAmount,
        String deliveryCity,
        String deliveryAddress,
        Instant deliveryDate
) {
}
