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
        User currentUser = userService.getUserProfile();
        SearchCriteria criteria = convertUserToSearchCriteria(currentUser);
        List<Restaurant> filteredRestaurants = restaurantService.searchRestaurants(criteria);
        return rankingService.rankRestaurants(filteredRestaurants, criteria);
    }

    private SearchCriteria convertUserToSearchCriteria(User user) {
        return new SearchCriteria(
                user.getFavoriteCuisines(),
                user.getDefaultBudget(),
                null,
                user.getDietaryOptions(),
                null,
                null,
                null,
                user.getFavoriteAmbienceTags(),
                user.getFavoriteOccasionTags(),
                null ,
                null,
                null,
                null
        );
    }
}