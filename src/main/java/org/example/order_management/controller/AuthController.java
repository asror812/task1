package org.example.order_management.controller;

import lombok.RequiredArgsConstructor;
import org.example.order_management.dto.CustomerResponseDto;
import org.example.order_management.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final CustomerService customerService;

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(
                Map.of("id", jwt.getSubject(),
                        "preferred_username", jwt.getClaimAsString("preferred_username"),
                        "email", jwt.getClaimAsString("email"))
        );
    }

    @GetMapping("/v2/me")
    public ResponseEntity<CustomerResponseDto> getMe(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(customerService.getMe(jwt));
    }

    @GetMapping("/claims")
    public ResponseEntity<Map<String, Object>> claims(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(jwt.getClaims());
    }
}
