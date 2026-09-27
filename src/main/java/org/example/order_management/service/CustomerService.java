package org.example.order_management.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.order_management.dto.CustomerResponseDto;
import org.example.order_management.model.Customer;
import org.example.order_management.repository.CustomerRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer findByKeyCloakId(String keycloakId) {
        return customerRepository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id " + keycloakId + " not found"));
    }


    public CustomerResponseDto getMe(Jwt jwt) {
        log.error("Subject: -> {}",jwt.getSubject());
        return customerRepository.findByKeycloakId(jwt.getSubject())
                .map(c -> new CustomerResponseDto(c.getFullName(), c.getEmail(), c.getPhone(), c.getKeycloakId()))
                .orElseThrow(() -> new EntityNotFoundException("Customer is not linked to Keycloak"));
    }
}
