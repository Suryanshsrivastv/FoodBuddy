package com.example.Restaurant_Suggestion.dto;
public record RegisterRequest(
        String username,
        String email,
        String password
) {}