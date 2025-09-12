package com.example.Restaurant_Suggestion.Controller;

import com.example.Restaurant_Suggestion.Models.Restaurant;
import com.example.Restaurant_Suggestion.Service.GeminiAiService;
import com.example.Restaurant_Suggestion.Service.RankingService;
import com.example.Restaurant_Suggestion.Service.RestaurantService;
import com.example.Restaurant_Suggestion.dto.RankedRestaurant;
import com.example.Restaurant_Suggestion.dto.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<RankedRestaurant> suggestRestaurants(@RequestParam String query) {
        // Step 1: AI extracts criteria
        SearchCriteria criteria = geminiAiService.getSearchCriteria(query);

        // Step 2: Service finds matching restaurants
        List<Restaurant> filteredRestaurants = restaurantService.searchRestaurants(criteria);

        // Step 3: Rank the results
        return rankingService.rankRestaurants(filteredRestaurants , criteria);
    }

    @GetMapping("/filter")
    public List<Restaurant> filterRestaurants(@ModelAttribute SearchCriteria criteria) {
        return restaurantService.searchRestaurants(criteria);
    }

//    @GetMapping("/search")
//    public List<Restaurant> searchRestaurants(
//            @RequestParam(required = false) Optional<String> cuisine,
//            @RequestParam(required = false) Optional<Integer> maxPriceLevel) {
//        return restaurantService.searchRestaurants(cuisine, maxPriceLevel);
//    }
}
