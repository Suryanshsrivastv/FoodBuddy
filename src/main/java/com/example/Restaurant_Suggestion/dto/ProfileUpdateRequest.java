package com.example.Restaurant_Suggestion.dto;

import java.util.List;

public record ProfileUpdateRequest(
        List<String> favoriteCuisines,
        Integer defaultBudget,
        List<String> dietaryOptions,
        String homeAddress,
        String workAddress,
        List<String> favoriteAmbienceTags,
        List<String> favoriteOccasionTags
) {}