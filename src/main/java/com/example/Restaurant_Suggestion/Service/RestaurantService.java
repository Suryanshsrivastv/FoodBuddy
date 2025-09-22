package com.example.Restaurant_Suggestion.Service;

import com.example.Restaurant_Suggestion.Models.Restaurant;
import com.example.Restaurant_Suggestion.Repo.RestaurantRepository;
import com.example.Restaurant_Suggestion.dto.SearchCriteria;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    private final MongoTemplate mongoTemplate;
    public RestaurantService(RestaurantRepository restaurantRepository, MongoTemplate mongoTemplate) {
        this.restaurantRepository = restaurantRepository;
        this.mongoTemplate = mongoTemplate;
    }
    @PostConstruct
    public void seedDatabase() {
        restaurantRepository.deleteAll();

        List<Restaurant> restaurants = List.of(
                new Restaurant("Jaipur Modern", "51, Sardar Patel Marg", "Jaipur", "fusion", 4.5, 3, "chic", 2000, List.of("fusion", "continental"), List.of("Quinoa Salad", "Avocado Toast"), List.of("vegetarian", "vegan", "gluten_free"), true, true, true, List.of("quiet", "chic"), List.of("business_lunch", "date_night"), true, List.of("Internal"), true, new GeoJsonPoint(75.7891, 26.9111)),
                new Restaurant("Chokhi Dhani", "12 Miles Tonk Road", "Jaipur", "rajasthani", 4.8, 3, "traditional", 1200, List.of("rajasthani", "north indian"), List.of("Dal Baati Churma"), List.of("vegetarian"), true, false, true, List.of("live_music", "traditional"), List.of("family_dinner"), true, List.of("Internal"), false, new GeoJsonPoint(75.8134, 26.7909)),
                new Restaurant("Tapri Central", "B4-E, Prithviraj Road", "Jaipur", "cafe", 4.6, 2, "casual", 800, List.of("cafe", "fast food"), List.of("Masala Chai", "Vada Pav"), List.of("vegetarian"), false, false, false, List.of("casual", "rooftop"), List.of("friends_hangout"), true, List.of("Zomato", "Swiggy"), false, new GeoJsonPoint(75.8083, 26.9037)),
                new Restaurant("The Forresta", "Devraj Niwas, Bani Park", "Jaipur", "multi-cuisine", 4.3, 4, "romantic", 2500, List.of("multi-cuisine", "north indian"), List.of("Lal Maas"), List.of("non_vegetarian"), true, true, true, List.of("romantic", "outdoor_seating"), List.of("date_night"), true, List.of("Zomato"), true, new GeoJsonPoint(75.7955, 26.9275)),
                new Restaurant("Annapoorna Bhavan", "T. Nagar", "Chennai", "south indian", 4.5, 1, "traditional", 200, List.of("south indian"), List.of("Filter Coffee", "Pongal"), List.of("vegetarian", "jain", "gluten_free"), true, false, false, List.of("traditional", "family"), List.of("family_dining"), true, List.of(), false, new GeoJsonPoint(80.2323, 13.0427)),
                new Restaurant("The Dosa Factory", "Banjara Hills", "Hyderabad", "south indian", 4.6, 1, "modern", 300, List.of("south indian"), List.of("50+ Dosa Varieties"), List.of("vegetarian", "non_vegetarian", "vegan", "keto_options"), true, true, true, List.of("modern", "minimal"), List.of("date_night", "friends_hangout"), true, List.of(), false, new GeoJsonPoint(78.4350, 17.4150)),
                new Restaurant("Madras Diaries", "Koregaon Park", "Pune", "south indian", 4.7, 1, "rustic-chic", 350, List.of("south indian"), List.of("Mini Tiffin", "Filter Coffee"), List.of("vegetarian", "organic_ingredients"), true, true, true, List.of("rustic_chic"), List.of("brunch", "business_meet"), true, List.of(), false, new GeoJsonPoint(73.8918, 18.5361)),
                new Restaurant("Ayyanar’s Chettinad Kitchen", "Anna Nagar", "Chennai", "chettinad", 4.4, 1, "heritage-inspired", 400, List.of("chettinad", "south indian"), List.of("Mutton Sukka", "Fish Fry"), List.of("non_vegetarian", "high_protein"), true, false, false, List.of("heritage_inspired"), List.of("family_dining", "weekend_dinner"), true, List.of(), true, new GeoJsonPoint(80.2163, 13.0863)),
                new Restaurant("Rayar’s Café", "Mylapore", "Chennai", "tamil nadu tiffin", 4.2, 1, "nostalgic", 150, List.of("tamil nadu tiffin"), List.of("Rava Dosa", "Filter Coffee"), List.of("vegetarian", "low_calorie"), false, false, false, List.of("nostalgic", "vintage"), List.of("solo_breakfast", "quick_bite"), false, List.of(), false, new GeoJsonPoint(80.2599, 13.0335)),
                new Restaurant("Kerala Sadya House", "Indiranagar", "Bangalore", "kerala", 4.5, 1, "cozy", 350, List.of("kerala"), List.of("Onam Sadya", "Avial", "Karimeen Pollichathu"), List.of("vegetarian", "non_vegetarian", "vegan", "gluten_free"), false, true, false, List.of("cozy", "traditional"), List.of("family_dining", "festive_meal"), true, List.of(), false, new GeoJsonPoint(77.6412, 12.9719)),
                new Restaurant("Dakshin Delights", "Jubilee Hills", "Hyderabad", "andhra", 4.6, 1, "elegant", 400, List.of("andhra", "telangana"), List.of("Andhra Meals", "Gongura Mutton"), List.of("vegetarian", "non_vegetarian", "high_protein", "spicy"), true, true, true, List.of("elegant", "heritage"), List.of("date_night", "business_meet"), true, List.of(), false, new GeoJsonPoint(78.4111, 17.4255)),
                new Restaurant("Malabar Coast Kitchen", "Fort Kochi", "Kochi", "malabar", 4.3, 2, "coastal", 500, List.of("malabar", "kerala"), List.of("Malabar Biryani", "Fish Moilee"), List.of("non_vegetarian", "dairy_free", "low_carb"), true, true, true, List.of("coastal", "artistic"), List.of("special_occasion", "dinner"), true, List.of(), true, new GeoJsonPoint(76.2413, 9.9658)),
                new Restaurant("Udupi Utsav", "Vashi", "Navi Mumbai", "karnataka", 4.4, 1, "clean", 200, List.of("karnataka", "udupi"), List.of("Goli Baje", "Udupi Sambar"), List.of("vegetarian", "jain", "satvik"), false, true, false, List.of("clean", "casual"), List.of("family_dining", "office_lunch"), true, List.of(), false, new GeoJsonPoint(72.9995, 19.0700)),
                new Restaurant("Thatte Idli House", "Basavanagudi", "Bangalore", "karnataka street food", 4.1, 1, "simple", 120, List.of("karnataka street food"), List.of("Thatte Idli", "Vada"), List.of("vegetarian", "low_sodium", "satvik"), false, false, false, List.of("simple", "mess_style"), List.of("breakfast", "budget_dining"), false, List.of(), false, new GeoJsonPoint(77.5705, 12.9416)),
                new Restaurant("Coastal Bay by Chettinadu Tales", "Banjara Hills", "Hyderabad", "chettinad", 4.7, 2, "upscale", 550, List.of("chettinad", "seafood", "fusion"), List.of("Crab Roast", "Chettinad Chicken"), List.of("non_vegetarian", "high_protein", "keto"), true, true, true, List.of("upscale", "thematic"), List.of("celebration", "dinner"), true, List.of(), true, new GeoJsonPoint(78.4419, 17.4140)),
                new Restaurant("Sambar Nation", "Bandra", "Mumbai", "south indian fusion", 4.2, 1, "modern", 280, List.of("south indian fusion"), List.of("Sambar Shots", "Dosa Tacos"), List.of("vegetarian", "vegan_options", "organic"), false, true, false, List.of("modern", "cafe_style"), List.of("friends_hangout", "brunch"), true, List.of(), false, new GeoJsonPoint(72.8353, 19.0544)),
                new Restaurant("Amma’s Military Mess", "Velachery", "Chennai", "tamil nadu military hotel", 4.4, 1, "rustic", 300, List.of("tamil nadu military hotel"), List.of("Mutton Biryani", "Chicken Chukka"), List.of("non_vegetarian", "spicy", "paleo"), false, false, false, List.of("rustic", "local"), List.of("casual_dinner", "local_taste"), true, List.of(), false, new GeoJsonPoint(80.2201, 12.9778)),
                new Restaurant("Mysuru Mane", "Jayanagar", "Bangalore", "karnataka royal cuisine", 4.6, 2, "royal", 450, List.of("karnataka royal cuisine"), List.of("Mysore Pak", "Ragi Mudde"), List.of("vegetarian", "non_vegetarian", "millet_based", "gluten_free"), true, true, true, List.of("royal_decor"), List.of("family_gathering", "date_night"), true, List.of(), false, new GeoJsonPoint(77.5794, 12.9231)),
                new Restaurant("Focaccia (Hyatt Regency)", "Anna Salai", "Chennai", "italian", 4.4, 3, "casual", 1500, List.of("italian"), List.of("Handmade pasta", "Italian brunch"), List.of("vegetarian", "non_vegetarian", "gluten_free_on_request"), true, true, true, List.of("casual_elegant"), List.of("brunch", "leisure_dining"), true, List.of(), true, new GeoJsonPoint(80.2520, 13.0485)),
                new Restaurant("Nolita", "Nungambakkam", "Chennai", "italian", 4.5, 3, "trendy", 1200, List.of("italian", "pizza"), List.of("Sourdough pizzas"), List.of("vegetarian", "non_vegetarian", "vegan_cheese", "whole_wheat_crust"), false, true, false, List.of("trendy", "casual"), List.of("casual_meetups"), true, List.of(), true, new GeoJsonPoint(80.2458, 13.0592)),
                new Restaurant("Toscano (Phoenix)", "Phoenix Market City", "Chennai", "italian", 4.3, 3, "stylish", 1250, List.of("italian"), List.of("Classic Italian", "Tiramisu"), List.of("vegetarian", "non_vegetarian", "vegan", "keto", "low_carb"), true, true, true, List.of("stylish"), List.of("family_dining", "casual_lunch"), true, List.of(), true, new GeoJsonPoint(80.2173, 12.9912)),
                new Restaurant("Dinevo", "Nungambakkam", "Chennai", "italian", 4.9, 2, "modern", 1000, List.of("italian", "continental"), List.of("Fusion Platters", "Pasta"), List.of("vegetarian", "non_vegetarian", "gluten_free", "vegan", "dairy_free"), true, true, true, List.of("modern", "bright"), List.of("date_night", "fine_dining"), true, List.of(), true, new GeoJsonPoint(80.2496, 13.0628)),
                new Restaurant("Luma Lounge", "Thousand Lights", "Chennai", "continental", 4.8, 3, "chic", 1500, List.of("continental", "italian"), List.of("Cheese Platters"), List.of("vegetarian", "non_vegetarian", "gluten_free_options"), true, true, false, List.of("chic", "lounge"), List.of("special_lunch"), true, List.of(), true, new GeoJsonPoint(80.2554, 13.0598)),
                new Restaurant("Suzette", "Bandra", "Mumbai", "french", 4.6, 2, "cozy", 1000, List.of("french", "crêperie"), List.of("Savory crêpes"), List.of("vegetarian", "non_vegetarian", "vegan", "organic", "buckwheat_crêpes"), false, true, false, List.of("cozy", "cafe_style"), List.of("casual_hangouts"), true, List.of(), true, new GeoJsonPoint(72.8258, 19.0631)),
                new Restaurant("Soufflé S’il Vous Plaît", "South Mumbai", "Mumbai", "french", 4.7, 3, "elegant", 2000, List.of("french", "bistro"), List.of("Savory Soufflés"), List.of("vegetarian", "non_vegetarian", "egg_free_desserts"), true, true, true, List.of("elegant", "thematic"), List.of("date_night", "celebration"), true, List.of(), true, new GeoJsonPoint(72.8184, 18.9328)),
                new Restaurant("Mockingbird Cafe Bar", "Churchgate", "Mumbai", "italian", 4.4, 4, "contemporary", 2500, List.of("italian", "french", "fusion"), List.of("Truffle Fries"), List.of("vegetarian", "non_vegetarian", "vegan", "keto_friendly"), true, true, true, List.of("contemporary", "cafe_bar"), List.of("casual_meetups"), true, List.of(), true, new GeoJsonPoint(72.8255, 18.9340)),
                new Restaurant("Carnival Evening Restaurant", "Thane", "Mumbai", "multicuisine", 4.2, 3, "open-air", 1250, List.of("multicuisine", "french"), List.of("BBQ"), List.of("vegetarian", "non_vegetarian", "gluten_free_on_request"), true, true, false, List.of("open_air", "family"), List.of("family_dining", "casual_dinner"), true, List.of(), true, new GeoJsonPoint(72.9780, 19.2183)),
                new Restaurant("Peshawari (ITC Grand Chola)", "Guindy", "Chennai", "north indian", 4.6, 3, "rustic", 2000, List.of("north indian", "mughlai"), List.of("Dal Bukhara", "Kebabs"), List.of("vegetarian", "non_vegetarian", "high_protein", "nut_free_available"), true, true, true, List.of("rustic_luxury"), List.of("celebration", "traditional_dining"), true, List.of(), true, new GeoJsonPoint(80.2206, 13.0116)));

        restaurantRepository.saveAll(restaurants);
        System.out.println("---- Database re-seeded with " + restaurants.size() + " location-aware restaurant entries ----");
    }
    public List<Restaurant> searchRestaurants(SearchCriteria criteria) {
        Query query = new Query();

        // --- NEW: Geospatial Filtering Logic ---
        if (criteria.userLat() != null && criteria.userLon() != null && criteria.maxDistanceKm() != null && criteria.maxDistanceKm() > 0) {
            // The Point object requires longitude first, then latitude
            Point userPoint = new Point(criteria.userLon(), criteria.userLat());

            // We create a Distance object to handle unit conversions (km -> meters)
            Distance maxDistance = new Distance(criteria.maxDistanceKm(), Metrics.KILOMETERS);

            query.addCriteria(Criteria.where("location").nearSphere(userPoint).maxDistance(maxDistance.getNormalizedValue()));
        }

        // --- Your existing, proven filter logic remains the same ---
        if (criteria.cuisines() != null && !criteria.cuisines().isEmpty()) {
            query.addCriteria(Criteria.where("cuisines").all(criteria.cuisines()));
        }
        if (criteria.dietaryOptions() != null && !criteria.dietaryOptions().isEmpty()) {
            query.addCriteria(Criteria.where("dietaryOptions").all(criteria.dietaryOptions()));
        }
        if (criteria.ambienceTags() != null && !criteria.ambienceTags().isEmpty()) {
            query.addCriteria(Criteria.where("ambienceTags").all(criteria.ambienceTags()));
        }
        if (criteria.occasionTags() != null && !criteria.occasionTags().isEmpty()) {
            query.addCriteria(Criteria.where("occasionTags").all(criteria.occasionTags()));
        }
        if (criteria.maxPrice() != null) {
            query.addCriteria(Criteria.where("averageCostPerPerson").lte(criteria.maxPrice()));
        }
        if (criteria.hasParking() != null && criteria.hasParking()) {
            query.addCriteria(Criteria.where("hasParking").is(true));
        }
        if (criteria.isWheelchairAccessible() != null && criteria.isWheelchairAccessible()) {
            query.addCriteria(Criteria.where("isWheelchairAccessible").is(true));
        }
        if (criteria.acceptsReservations() != null && criteria.acceptsReservations()) {
            query.addCriteria(Criteria.where("acceptsReservations").is(true));
        }
        if (criteria.servesAlcohol() != null && criteria.servesAlcohol()) {
            query.addCriteria(Criteria.where("servesAlcohol").is(true));
        }

        return mongoTemplate.find(query, Restaurant.class);
    }
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
}
