package com.example.Restaurant_Suggestion.Service;

import com.example.Restaurant_Suggestion.Models.Restaurant;
import com.example.Restaurant_Suggestion.Repo.RestaurantRepository;
import com.example.Restaurant_Suggestion.dto.SearchCriteria;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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

        Restaurant r1 = new Restaurant("Jaipur Modern", "51, Sardar Patel Marg", "Jaipur", "fusion", 4.5, 3, "chic", 2000, List.of("fusion", "continental"), List.of("quinoa salad", "avocado toast"), List.of("vegetarian", "vegan", "gluten_free"), true, true, true, List.of("quiet", "chic"), List.of("business_lunch", "date_night"), true, List.of("internal"), true);
        Restaurant r2 = new Restaurant("Chokhi Dhani", "12 Miles Tonk Road", "Jaipur", "rajasthani", 4.8, 2, "traditional", 1200, List.of("rajasthani", "north indian"), List.of("dal baati churma"), List.of("vegetarian"), true, false, true, List.of("live_music", "traditional"), List.of("family_dinner"), true, List.of("internal"), false);
        Restaurant r3 = new Restaurant("Tapri Central", "B4-E, Prithviraj Road", "Jaipur", "cafe", 4.6, 2, "casual", 800, List.of("cafe", "fast food"), List.of("masala chai", "vada pav"), List.of("vegetarian"), false, false, false, List.of("casual", "rooftop"), List.of("friends_hangout"), true, List.of("zomato", "swiggy"), false);
        Restaurant r4 = new Restaurant("The Forresta", "Devraj Niwas, Bani Park", "Jaipur", "multi-cuisine", 4.3, 4, "romantic", 2500, List.of("multi-cuisine", "north indian"), List.of("lal maas"), List.of("non_vegetarian"), true, true, true, List.of("romantic", "outdoor_seating"), List.of("date_night"), true, List.of("zomato"), true);
        Restaurant r5 = new Restaurant("Annapoorna Bhavan", "T. Nagar", "Chennai", "south indian", 4.5, 1, "traditional", 200, List.of("south indian"), List.of("filter coffee", "pongal"), List.of("vegetarian", "jain", "gluten_free"), true, false, false, List.of("traditional", "family"), List.of("family_dining"), true, List.of(), false);
        Restaurant r6 = new Restaurant("Namma Veedu Mess", "JP Nagar", "Bangalore", "south indian", 4.3, 1, "casual", 250, List.of("south indian"), List.of("chicken chettinad", "meals"), List.of("non_vegetarian", "high_protein", "spicy"), false, false, false, List.of("casual", "mess_style"), List.of("quick_lunch", "office_lunch"), true, List.of(), false);
        Restaurant r7 = new Restaurant("The Dosa Factory", "Banjara Hills", "Hyderabad", "south indian", 4.6, 1, "modern", 300, List.of("south indian"), List.of("50+ dosa varieties"), List.of("vegetarian", "non_vegetarian", "vegan", "keto_options"), true, true, true, List.of("modern", "minimal"), List.of("date_night", "friends_hangout"), true, List.of(), false);
        Restaurant r8 = new Restaurant("Madras Diaries", "Koregaon Park", "Pune", "south indian", 4.7, 1, "rustic-chic", 350, List.of("south indian"), List.of("mini tiffin", "filter coffee"), List.of("vegetarian", "organic_ingredients"), true, true, true, List.of("rustic_chic"), List.of("brunch", "business_meet"), true, List.of(), false);
        Restaurant r9 = new Restaurant("Ayyanar’s Chettinad Kitchen", "Anna Nagar", "Chennai", "chettinad", 4.4, 1, "heritage-inspired", 400, List.of("chettinad", "south indian"), List.of("mutton sukka", "fish fry"), List.of("non_vegetarian", "high_protein"), true, false, false, List.of("heritage_inspired"), List.of("family_dining", "weekend_dinner"), true, List.of(), true);
        Restaurant r10 = new Restaurant("Rayar’s Café", "Mylapore", "Chennai", "tamil nadu tiffin", 4.2, 1, "nostalgic", 150, List.of("tamil nadu tiffin"), List.of("rava dosa", "filter coffee"), List.of("vegetarian", "low_calorie"), false, false, false, List.of("nostalgic", "vintage"), List.of("solo_breakfast", "quick_bite"), false, List.of(), false);
        Restaurant r11 = new Restaurant("Kerala Sadya House", "Indiranagar", "Bangalore", "kerala", 4.5, 1, "cozy", 350, List.of("kerala"), List.of("onam sadya", "avial", "karimeen pollichathu"), List.of("vegetarian", "non_vegetarian", "vegan", "gluten_free"), false, true, false, List.of("cozy", "traditional"), List.of("family_dining", "festive_meal"), true, List.of(), false);
        Restaurant r12 = new Restaurant("Dakshin Delights", "Jubilee Hills", "Hyderabad", "andhra", 4.6, 1, "elegant", 400, List.of("andhra", "telangana"), List.of("andhra meals", "gongura mutton"), List.of("vegetarian", "non_vegetarian", "high_protein", "spicy"), true, true, true, List.of("elegant", "heritage"), List.of("date_night", "business_meet"), true, List.of(), false);
        Restaurant r13 = new Restaurant("Malabar Coast Kitchen", "Fort Kochi", "Kochi", "malabar", 4.3, 2, "coastal", 500, List.of("malabar", "kerala"), List.of("malabar biryani", "fish moilee"), List.of("non_vegetarian", "dairy_free", "low_carb"), true, true, true, List.of("coastal", "artistic"), List.of("special_occasion", "dinner"), true, List.of(), true);
        Restaurant r14 = new Restaurant("Udupi Utsav", "Vashi", "Navi Mumbai", "karnataka", 4.4, 1, "clean", 200, List.of("karnataka", "udupi"), List.of("goli baje", "udupi sambar"), List.of("vegetarian", "jain", "satvik"), false, true, false, List.of("clean", "casual"), List.of("family_dining", "office_lunch"), true, List.of(), false);
        Restaurant r15 = new Restaurant("Thatte Idli House", "Basavanagudi", "Bangalore", "karnataka street food", 4.1, 1, "simple", 120, List.of("karnataka street food"), List.of("thatte idli", "vada"), List.of("vegetarian", "low_sodium", "satvik"), false, false, false, List.of("simple", "mess_style"), List.of("breakfast", "budget_dining"), false, List.of(), false);
        Restaurant r16 = new Restaurant("Coastal Bay by Chettinadu Tales", "Banjara Hills", "Hyderabad", "chettinad", 4.7, 2, "upscale", 550, List.of("chettinad", "seafood", "fusion"), List.of("crab roast", "chettinad chicken"), List.of("non_vegetarian", "high_protein", "keto"), true, true, true, List.of("upscale", "thematic"), List.of("celebration", "dinner"), true, List.of(), true);
        Restaurant r17 = new Restaurant("Sambar Nation", "Bandra", "Mumbai", "south indian fusion", 4.2, 1, "modern", 280, List.of("south indian fusion"), List.of("sambar shots", "dosa tacos"), List.of("vegetarian", "vegan_options", "organic"), false, true, false, List.of("modern", "cafe_style"), List.of("friends_hangout", "brunch"), true, List.of(), false);
        Restaurant r18 = new Restaurant("Amma’s Military Mess", "Velachery", "Chennai", "tamil nadu military hotel", 4.4, 1, "rustic", 300, List.of("tamil nadu military hotel"), List.of("mutton biryani", "chicken chukka"), List.of("non_vegetarian", "spicy", "paleo"), false, false, false, List.of("rustic", "local"), List.of("casual_dinner", "local_taste"), true, List.of(), false);
        Restaurant r19 = new Restaurant("Mysuru Mane", "Jayanagar", "Bangalore", "karnataka royal cuisine", 4.6, 2, "royal", 450, List.of("karnataka royal cuisine"), List.of("mysore pak", "ragi mudde"), List.of("vegetarian", "non_vegetarian", "millet_based", "gluten_free"), true, true, true, List.of("royal_decor"), List.of("family_gathering", "date_night"), true, List.of(), false);
        Restaurant r20 = new Restaurant("Focaccia (Hyatt Regency)", "Anna Salai", "Chennai", "italian", 4.4, 3, "casual", 1500, List.of("italian"), List.of("handmade pasta", "italian brunch"), List.of("vegetarian", "non_vegetarian", "gluten_free_on_request"), true, true, true, List.of("casual_elegant"), List.of("brunch", "leisure_dining"), true, List.of(), true);
        Restaurant r21 = new Restaurant("Nolita", "Nungambakkam", "Chennai", "italian", 4.5, 3, "trendy", 1200, List.of("italian", "pizza"), List.of("sourdough pizzas"), List.of("vegetarian", "non_vegetarian", "vegan_cheese", "whole_wheat_crust"), false, true, false, List.of("trendy", "casual"), List.of("casual_meetups"), true, List.of(), true);
        Restaurant r22 = new Restaurant("Toscano (Phoenix)", "Phoenix Market City", "Chennai", "italian", 4.3, 3, "stylish", 1250, List.of("italian"), List.of("classic italian", "tiramisu"), List.of("vegetarian", "non_vegetarian", "vegan", "keto", "low_carb"), true, true, true, List.of("stylish"), List.of("family_dining", "casual_lunch"), true, List.of(), true);
        Restaurant r23 = new Restaurant("Dinevo", "Nungambakkam", "Chennai", "italian", 4.9, 2, "modern", 1000, List.of("italian", "continental"), List.of("fusion platters", "pasta"), List.of("vegetarian", "non_vegetarian", "gluten_free", "vegan", "dairy_free"), true, true, true, List.of("modern", "bright"), List.of("date_night", "fine_dining"), true, List.of(), true);
        Restaurant r24 = new Restaurant("Luma Lounge", "Thousand Lights", "Chennai", "continental", 4.8, 3, "chic", 1500, List.of("continental", "italian"), List.of("continental bowls", "cheese platters"), List.of("vegetarian", "non_vegetarian", "gluten_free_options"), true, true, false, List.of("chic", "lounge"), List.of("brunch", "special_lunch"), true, List.of(), true);
        Restaurant r25 = new Restaurant("Suzette", "Bandra", "Mumbai", "french", 4.6, 2, "cozy", 1000, List.of("french", "crêperie"), List.of("savory & sweet crêpes"), List.of("vegetarian", "non_vegetarian", "vegan", "organic", "buckwheat_crêpes"), false, true, false, List.of("cozy", "cafe_style"), List.of("casual_hangouts"), true, List.of(), true);
        Restaurant r26 = new Restaurant("Soufflé S’il Vous Plaît", "South Mumbai", "Mumbai", "french", 4.7, 3, "elegant", 2000, List.of("french", "bistro"), List.of("savory soufflés", "ratatouille"), List.of("vegetarian", "non_vegetarian", "egg_free_desserts"), true, true, true, List.of("elegant", "thematic"), List.of("date_night", "celebration"), true, List.of(), true);
        Restaurant r27 = new Restaurant("Mockingbird Cafe Bar", "Churchgate", "Mumbai", "italian", 4.4, 4, "contemporary", 2500, List.of("italian", "french", "fusion"), List.of("truffle fries", "mixed platters"), List.of("vegetarian", "non_vegetarian", "vegan", "keto_friendly"), true, true, true, List.of("contemporary", "cafe_bar"), List.of("evenings", "casual_meetups"), true, List.of(), true);
        Restaurant r28 = new Restaurant("Carnival Evening Restaurant", "Thane", "Mumbai", "multicuisine", 4.2, 3, "open-air", 1250, List.of("multicuisine", "french"), List.of("fusion starters", "bbq"), List.of("vegetarian", "non_vegetarian", "gluten_free_on_request"), true, true, false, List.of("open_air", "family"), List.of("family_dining", "casual_dinner"), true, List.of(), true);
        Restaurant r29 = new Restaurant("Peshawari (ITC Grand Chola)", "Guindy", "Chennai", "north indian", 4.6, 3, "rustic", 2000, List.of("north indian", "mughlai"), List.of("dal bukhara", "kebabs", "roti basket"), List.of("vegetarian", "non_vegetarian", "high_protein", "nut_free_available"), true, true, true, List.of("rustic_luxury"), List.of("celebration", "traditional_dining"), true, List.of(), true);

        List<Restaurant> restaurants = List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13,
                r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29);

        restaurantRepository.saveAll(restaurants);
        System.out.println("---- Database re-seeded with " + restaurants.size() + " fully-aligned restaurant entries ----");
    }
    public List<Restaurant> searchRestaurants(SearchCriteria criteria) {
        Query query = new Query();

        // --- The logic for list-based fields has been changed from .in() to .all() ---

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

        // --- The logic for single-value fields remains the same ---

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

        if (query.getQueryObject().isEmpty()) {
            return List.of();
        }

        return mongoTemplate.find(query, Restaurant.class);
    }
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}
