package com.example.Restaurant_Suggestion.Controller;

import com.example.Restaurant_Suggestion.Service.AuthService;
import com.example.Restaurant_Suggestion.dto.AuthResponse;
import com.example.Restaurant_Suggestion.dto.LoginRequest;
import com.example.Restaurant_Suggestion.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        authService.register(registerRequest);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}
