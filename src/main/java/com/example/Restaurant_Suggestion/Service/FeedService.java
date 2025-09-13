package com.example.Restaurant_Suggestion.Service;

import com.example.Restaurant_Suggestion.Models.Restaurant;
import com.example.Restaurant_Suggestion.Models.User;
import com.example.Restaurant_Suggestion.dto.RankedRestaurant;
import com.example.Restaurant_Suggestion.dto.SearchCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {

    private final UserService userService;
    private final RestaurantService restaurantService;
    private final RankingService rankingService;

    public FeedService(UserService userService, RestaurantService restaurantService, RankingService rankingService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.rankingService = rankingService;
    }

    public List<RankedRestaurant> generatePersonalizedFeed() {
        // 1. Get the currently logged-in user's profile
        User currentUser = userService.getUserProfile();

        // 2. Convert their saved preferences into a SearchCriteria object
        SearchCriteria criteria = convertUserToSearchCriteria(currentUser);

        // 3. Find restaurants that match their preferences
        List<Restaurant> filteredRestaurants = restaurantService.searchRestaurants(criteria);

        // 4. Rank the results to find the best matches
        return rankingService.rankRestaurants(filteredRestaurants, criteria);
    }

    /**
     * A helper method to map a User's profile to a SearchCriteria DTO.
     */
    private SearchCriteria convertUserToSearchCriteria(User user) {
        return new SearchCriteria(
                user.getFavoriteCuisines(),
                user.getDefaultBudget(),
                user.getDietaryOptions(),
                null, // hasParking is not a saved preference
                null, // isWheelchairAccessible is not a saved preference
                null, // acceptsReservations is not a saved preference
                user.getFavoriteAmbienceTags(),
                user.getFavoriteOccasionTags(),
                null // servesAlcohol is not a saved preference
        );
    }
}