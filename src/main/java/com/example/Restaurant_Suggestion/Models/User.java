package com.example.Restaurant_Suggestion.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    // --- Core Credentials ---
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;

    // --- Expanded Preferences & Data ---
    private List<String> favoriteCuisines;
    private Integer defaultBudget;
    private List<String> dietaryOptions;
    private String homeAddress;
    private String workAddress;
    private List<String> favoriteAmbienceTags;
    private List<String> favoriteOccasionTags;
    private List<String> bookmarkedRestaurantIds;

    // --- Constructors ---
    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // --- GETTERS AND SETTERS (This is the crucial fix) ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<String> getFavoriteCuisines() { return favoriteCuisines; }
    public void setFavoriteCuisines(List<String> favoriteCuisines) { this.favoriteCuisines = favoriteCuisines; }
    public Integer getDefaultBudget() { return defaultBudget; }
    public void setDefaultBudget(Integer defaultBudget) { this.defaultBudget = defaultBudget; }
    public List<String> getDietaryOptions() { return dietaryOptions; }
    public void setDietaryOptions(List<String> dietaryOptions) { this.dietaryOptions = dietaryOptions; }
    public String getHomeAddress() { return homeAddress; }
    public void setHomeAddress(String homeAddress) { this.homeAddress = homeAddress; }
    public String getWorkAddress() { return workAddress; }
    public void setWorkAddress(String workAddress) { this.workAddress = workAddress; }
    public List<String> getFavoriteAmbienceTags() { return favoriteAmbienceTags; }
    public void setFavoriteAmbienceTags(List<String> favoriteAmbienceTags) { this.favoriteAmbienceTags = favoriteAmbienceTags; }
    public List<String> getFavoriteOccasionTags() { return favoriteOccasionTags; }
    public void setFavoriteOccasionTags(List<String> favoriteOccasionTags) { this.favoriteOccasionTags = favoriteOccasionTags; }
    public List<String> getBookmarkedRestaurantIds() { return bookmarkedRestaurantIds; }
    public void setBookmarkedRestaurantIds(List<String> bookmarkedRestaurantIds) { this.bookmarkedRestaurantIds = bookmarkedRestaurantIds; }

    // --- UserDetails interface methods (rely on the getters/setters above) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) { this.username = username; }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }
}