package com.example.Restaurant_Suggestion.Repo;

import com.example.Restaurant_Suggestion.Models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    List<Restaurant> findByCuisine(String cuisine);

    // "find all restaurants where the priceLevel field is less than or equal to"
    List<Restaurant> findByPriceLevelLessThanEqual(int priceLevel);

    // "find all restaurants where both the cuisine and priceLevel fields match"
    List<Restaurant> findByCuisineAndPriceLevelLessThanEqual(String cuisine, int priceLevel);

}
