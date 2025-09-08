package com.example.Restaurant_Suggestion.Service;

import com.example.Restaurant_Suggestion.Models.Restaurant;
import com.example.Restaurant_Suggestion.Repo.RestaurantRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @PostConstruct
    public void seedDatabase() {
        restaurantRepository.deleteAll();

        Restaurant r1 = new Restaurant(null, "Jaipur Modern", "51, Sardar Patel Marg", "Jaipur", "Fusion", 4.5, 3, "chic");
        Restaurant r2 = new Restaurant(null, "Chokhi Dhani", "12 KM Tonk Road", "Jaipur", "Rajasthani", 4.8, 2, "traditional");
        Restaurant r3 = new Restaurant(null, "Tapri Central", "B4-E, Prithviraj Road", "Jaipur", "Cafe", 4.6, 2, "casual");
        Restaurant r4 = new Restaurant(null, "The Forresta", "Devraj Niwas, Bani Park", "Jaipur", "Multi-cuisine", 4.3, 3, "romantic");

        restaurantRepository.saveAll(List.of(r1, r2, r3, r4));

        System.out.println("---- Database seeded with sample restaurants ----");
    }
    public List<Restaurant> searchRestaurants(Optional<String> cuisine, Optional<Integer> maxPriceLevel, Optional<String> vibe) {
        boolean hasCuisine = cuisine.isPresent() && !cuisine.get().isBlank();
        boolean hasPrice = maxPriceLevel.isPresent();
        boolean hasVibe = vibe.isPresent() && !vibe.get().isBlank();

        // For simplicity in this example, we'll prioritize filters.
        // A more advanced implementation might build a single, more complex query.
        if (hasCuisine) {
            return restaurantRepository.findByCuisine(cuisine.get());
        } else if (hasVibe) {
            return restaurantRepository.findByVibe(vibe.get());
        } else if (hasPrice) {
            return restaurantRepository.findByPriceLevelLessThanEqual(maxPriceLevel.get());
        } else {
            // If the AI returns no criteria, return nothing to avoid showing all restaurants.
            return List.of();
        }
    }
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}
