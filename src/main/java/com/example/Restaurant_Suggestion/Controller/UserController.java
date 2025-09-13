package com.example.Restaurant_Suggestion.Controller;

import com.example.Restaurant_Suggestion.Models.User;
import com.example.Restaurant_Suggestion.Service.UserService;
import com.example.Restaurant_Suggestion.dto.ProfileUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUserProfile() {
        return ResponseEntity.ok(userService.getUserProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateUserProfile(@RequestBody ProfileUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUserProfile(request));
    }
}