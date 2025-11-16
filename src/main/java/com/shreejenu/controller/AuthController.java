package com.shreejenu.controller;

import com.shreejenu.dto.AuthRequest;
import com.shreejenu.dto.AuthResponse;
import com.shreejenu.dto.RegisterRequest;
import com.shreejenu.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String token = authService.login(authRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
