package org.example.order_management.controller;

import lombok.RequiredArgsConstructor;
import org.example.order_management.dto.CustomerResponseDto;
import org.example.order_management.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/me")
    public ResponseEntity<CustomerResponseDto> getMe(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(customerService.getMe(jwt));
    }
}
