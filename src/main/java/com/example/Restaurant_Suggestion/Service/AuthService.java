package com.example.Restaurant_Suggestion.Service;

import com.example.Restaurant_Suggestion.Models.User;
import com.example.Restaurant_Suggestion.Repo.UserRepository;
import com.example.Restaurant_Suggestion.config.Role;
import com.example.Restaurant_Suggestion.dto.AuthResponse;
import com.example.Restaurant_Suggestion.dto.LoginRequest;
import com.example.Restaurant_Suggestion.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        // 1. Authenticate the user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        // 2. If successful, find the user
        var user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new IllegalStateException("User not found after authentication"));

        // 3. Generate a JWT
        var jwtToken = jwtService.generateToken(user);

        // 4. Return the token in the response
        return new AuthResponse(jwtToken);
    }

    public void register(RegisterRequest registerRequest) {
        // 1. Check if username already exists
        if (userRepository.existsByUsername(registerRequest.username())) {
            throw new IllegalStateException("Username is already taken!");
        }

        // 2. Check if email already exists
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new IllegalStateException("Email is already in use!");
        }

        // 3. Create a new user object
        User user = new User(
                registerRequest.username(),
                registerRequest.email(),
                passwordEncoder.encode(registerRequest.password())
        );
        // Assign default role
        user.setRoles(Set.of(Role.USER));

        // Create a special admin user
        if ("Suryansh16".equals(registerRequest.username())) {
            user.setRoles(Set.of(Role.USER, Role.ADMIN));
        }
        // 5. Save the new user to the database
        userRepository.save(user);
    }
}
