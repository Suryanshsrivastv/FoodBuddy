package com.example.Restaurant_Suggestion.Controller;

import com.example.Restaurant_Suggestion.Models.Restaurant;
import com.example.Restaurant_Suggestion.Service.GeminiAiService;
import com.example.Restaurant_Suggestion.Service.RankingService;
import com.example.Restaurant_Suggestion.Service.RestaurantService;
import com.example.Restaurant_Suggestion.dto.RankedRestaurant;
import com.example.Restaurant_Suggestion.dto.SearchCriteria;
import com.example.Restaurant_Suggestion.dto.SuggestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
public class HomeController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private GeminiAiService geminiAiService;

    @Autowired
    private RankingService rankingService;
    @GetMapping("/home")
    private String home() {
        return "Welcome to Restaurant Suggestion API";
    }
    @GetMapping("/test")
    public String testAiService() {
        String criteria = geminiAiService.getAnswer("Explain how AI works in a few words");
        return criteria;
    }
    @GetMapping("/")
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("/suggest")
    public ResponseEntity<SuggestionResponse> suggestRestaurants(
            @RequestParam String query,
            @RequestParam(required = false) Double userLat,
            @RequestParam(required = false) Double userLon
    ) {
        SearchCriteria initialCriteria = geminiAiService.getSearchCriteria(query);

        // Create the full criteria object including location
        SearchCriteria fullCriteria = new SearchCriteria(
                initialCriteria.cuisines(), initialCriteria.maxPrice(), initialCriteria.city(), initialCriteria.dietaryOptions(),
                 initialCriteria.hasParking(), initialCriteria.isWheelchairAccessible(),
                initialCriteria.acceptsReservations(), initialCriteria.ambienceTags(), initialCriteria.occasionTags(),
                initialCriteria.servesAlcohol(), userLat, userLon, null // No distance limit on initial query
        );

        List<Restaurant> filteredRestaurants = restaurantService.searchRestaurants(fullCriteria);
        List<RankedRestaurant> rankedRestaurants = rankingService.rankRestaurants(filteredRestaurants, fullCriteria);

        if (!rankedRestaurants.isEmpty()) {
            SuggestionResponse response = new SuggestionResponse(false, "Here are the best matches for your search!", rankedRestaurants);
            return ResponseEntity.ok(response);
        }

        if (userLat != null && userLon != null) {
            System.out.println("No exact matches found. Triggering fallback search for nearby restaurants...");
            SearchCriteria fallbackCriteria = new SearchCriteria(null, null, null, null, null, null, null, null, null, null, userLat, userLon, 5.0);

            List<Restaurant> fallbackFiltered = restaurantService.searchRestaurants(fallbackCriteria);
            List<RankedRestaurant> fallbackRanked = rankingService.rankRestaurants(fallbackFiltered, fallbackCriteria);
            if (!fallbackRanked.isEmpty()) {
                SuggestionResponse response = new SuggestionResponse(true, "We couldn't find an exact match, but here are some highly-rated options nearby!", fallbackRanked);
                return ResponseEntity.ok(response);
            }
        }
        SuggestionResponse emptyResponse = new SuggestionResponse(false, "Sorry, no restaurants were found matching your criteria.", List.of());
        return ResponseEntity.ok(emptyResponse);
    }

    @GetMapping("/filter")
    public List<RankedRestaurant> filterRestaurants(@ModelAttribute SearchCriteria criteria) {
        List<Restaurant> filteredRestaurants = restaurantService.searchRestaurants(criteria);
        return rankingService.rankRestaurants(filteredRestaurants, criteria);
    }
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.status(201).body(savedRestaurant);
    }

}
