package com.example.Restaurant_Suggestion.dto;

import com.example.Restaurant_Suggestion.Models.Restaurant;

public record RankedRestaurant(
        Restaurant restaurant,
        double foodRelevanceScore
) {
}
