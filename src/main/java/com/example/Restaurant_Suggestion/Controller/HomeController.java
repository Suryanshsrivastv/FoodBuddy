package com.example.Restaurant_Suggestion.Controller;

import com.example.Restaurant_Suggestion.Models.Restaurant;
import com.example.Restaurant_Suggestion.Service.GeminiAiService;
import com.example.Restaurant_Suggestion.Service.RestaurantService;
import com.example.Restaurant_Suggestion.dto.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
public class HomeController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private GeminiAiService geminiAiService;
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
    public List<Restaurant> suggestRestaurants(@RequestParam String query) {
        // 1. Let the AI extract structured criteria from the user's query
        SearchCriteria criteria = geminiAiService.getSearchCriteria(query);

        // 2. Use the extracted criteria to search the database
        return restaurantService.searchRestaurants(
                Optional.ofNullable(criteria.cuisine()),
                Optional.ofNullable(criteria.maxPriceLevel()),
                Optional.ofNullable(criteria.vibe())
        );
    }
//    @GetMapping("/search")
//    public List<Restaurant> searchRestaurants(
//            @RequestParam(required = false) Optional<String> cuisine,
//            @RequestParam(required = false) Optional<Integer> maxPriceLevel) {
//        return restaurantService.searchRestaurants(cuisine, maxPriceLevel);
//    }
}
