package com.example.Restaurant_Suggestion.dto;

import java.util.List;

public record SearchCriteria(
        List<String> cuisines,
        Integer maxPrice,
        String city,
        List<String> dietaryOptions,
        Boolean hasParking,
        Boolean isWheelchairAccessible,
        Boolean acceptsReservations,
        List<String> ambienceTags,
        List<String> occasionTags,
        Boolean servesAlcohol,
        Double userLat,
        Double userLon,
        Double maxDistanceKm
) {
}

