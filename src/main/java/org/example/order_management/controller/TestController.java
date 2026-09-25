package org.example.order_management.controller;

import org.example.order_management.dto.MessageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/test")
    public ResponseEntity<MessageDto> test() {
        return ResponseEntity.ok(new MessageDto("Hello World", "from-public", "to-public"));
    }

    @GetMapping("/private/test")
    public ResponseEntity<MessageDto> testPrivate() {
        return ResponseEntity.ok(new MessageDto("Hello World", "from-private", "to-private"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/admin/test")
    public ResponseEntity<MessageDto> adminHello() {
        return ResponseEntity.ok(new MessageDto("Доступ разрешён: роль ADMIN", "from-admin", "to-admin"));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/api/customer/test")
    public ResponseEntity<MessageDto> customerHello() {
        return ResponseEntity.ok(new MessageDto("Доступ разрешён: роль CUSTOMER", "from-customer", "to-customer"));
    }
}
