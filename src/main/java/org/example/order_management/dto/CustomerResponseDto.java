package org.example.order_management.dto;


public record CustomerResponseDto(
        String fullName,
        String email,
        String phone,
        String keycloakId) {
}
