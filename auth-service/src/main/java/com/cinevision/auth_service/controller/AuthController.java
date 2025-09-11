package com.cinevision.auth_service.controller;

import com.cinevision.auth_service.dto.UserDTO;
import com.cinevision.auth_service.model.User;
import com.cinevision.auth_service.service.UserService;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor  // ✅ generates constructor for final fields
public class AuthController {

    private final UserService userService; // ✅ no @Autowired needed

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO) {
        User user = userService.registerUser(userDTO);
        return ResponseEntity.status(201).body(user);
    }

    @GetMapping("/user/{username}")
    public User getUser(@PathVariable String username) {
        return userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}