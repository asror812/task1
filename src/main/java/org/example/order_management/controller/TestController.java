package org.example.order_management.controller;

import org.example.order_management.dto.MessageDto;
import org.springframework.http.ResponseEntity;
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
}
