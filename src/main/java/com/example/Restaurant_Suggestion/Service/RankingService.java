package com.example.Restaurant_Suggestion.Service;

import com.example.Restaurant_Suggestion.Models.Restaurant;
import com.example.Restaurant_Suggestion.dto.RankedRestaurant;
import com.example.Restaurant_Suggestion.dto.SearchCriteria;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingService {

    public List<RankedRestaurant> rankRestaurants(List<Restaurant> restaurants, SearchCriteria criteria) {
        if (restaurants == null || restaurants.isEmpty()) {
            return List.of();
        }

        return restaurants.stream()
                .map(restaurant -> {
                    double score = calculateRelevanceScore(restaurant, criteria);
                    return new RankedRestaurant(restaurant, score);
                })
                .sorted(Comparator.comparingDouble(RankedRestaurant::foodRelevanceScore).reversed())
                .collect(Collectors.toList());
    }

    private double calculateRelevanceScore(Restaurant restaurant, SearchCriteria criteria) {
        // Start with the base rating of the restaurant
        double score = restaurant.getRating();

        // Add a bonus for each criterion that matches the user's request
        final double MATCH_BONUS = 0.5;

        if (criteria.cuisines() != null && !criteria.cuisines().isEmpty()) {
            if (restaurant.getCuisines().stream().anyMatch(c -> criteria.cuisines().contains(c))) {
                score += MATCH_BONUS;
            }
        }
        if (criteria.ambienceTags() != null && !criteria.ambienceTags().isEmpty()) {
            if (restaurant.getAmbienceTags().stream().anyMatch(a -> criteria.ambienceTags().contains(a))) {
                score += MATCH_BONUS;
            }
        }
        if (criteria.occasionTags() != null && !criteria.occasionTags().isEmpty()) {
            if (restaurant.getOccasionTags().stream().anyMatch(o -> criteria.occasionTags().contains(o))) {
                score += MATCH_BONUS;
            }
        }

        return score;
    }
}
