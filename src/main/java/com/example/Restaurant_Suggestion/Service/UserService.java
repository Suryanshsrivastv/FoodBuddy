package com.example.Restaurant_Suggestion.Service;

import com.example.Restaurant_Suggestion.Models.User;
import com.example.Restaurant_Suggestion.Repo.UserRepository;
import com.example.Restaurant_Suggestion.dto.ProfileUpdateRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User getCurrentlyLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserProfile() {
        return getCurrentlyLoggedInUser();
    }

    public User updateUserProfile(ProfileUpdateRequest request) {
        User currentUser = getCurrentlyLoggedInUser();
        // Update fields if they are provided in the request
        if (request.favoriteCuisines() != null) currentUser.setFavoriteCuisines(request.favoriteCuisines());
        if (request.defaultBudget() != null) currentUser.setDefaultBudget(request.defaultBudget());
        if (request.dietaryOptions() != null) currentUser.setDietaryOptions(request.dietaryOptions());
        if (request.homeAddress() != null) currentUser.setHomeAddress(request.homeAddress());
        if (request.workAddress() != null) currentUser.setWorkAddress(request.workAddress());
        if (request.favoriteAmbienceTags() != null) currentUser.setFavoriteAmbienceTags(request.favoriteAmbienceTags());
        if (request.favoriteOccasionTags() != null) currentUser.setFavoriteOccasionTags(request.favoriteOccasionTags());

        return userRepository.save(currentUser);
    }
}