package com.example.Restaurant_Suggestion.dto;

import java.util.List;

public record SuggestionResponse(
        boolean isFallback,
        String message,
        List<RankedRestaurant> restaurants
) {
}