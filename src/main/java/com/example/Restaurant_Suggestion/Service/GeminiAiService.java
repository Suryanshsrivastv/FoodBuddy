package com.example.Restaurant_Suggestion.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.Restaurant_Suggestion.dto.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiAiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public GeminiAiService(WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public String getAnswer(String question) {
        // Construct the request payload
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", question)
                        })
                }
        );

        // Make API Call
        String response = webClient.post()
                .uri("/v1beta/models/gemini-2.5-flash:generateContent")
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Return response
        return response;
    }
    public SearchCriteria getSearchCriteria(String userQuery) {
        String prompt = """
           You are an expert at extracting restaurant search criteria from a user's query into a JSON object.
    From the query: "%s", extract the following fields.
    
    IMPORTANT: All list items for cuisines, dietaryOptions, ambienceTags, and occasionTags MUST be lowercase and use underscores for spaces (e.g., "date_night", "south_indian").
    
    - cuisines (list of strings): e.g., "south_indian", "chinese"
    - maxPrice (integer): Interpret "cheap" as 800, "mid-range" as 1500, "expensive" as 3000.
    - dietaryOptions (list of strings): e.g., "vegetarian", "vegan", "gluten_free".
    - hasParking (boolean): true if the user mentions parking.
    - isWheelchairAccessible (boolean): true if user mentions wheelchair access.
    - acceptsReservations (boolean): true if user mentions booking.
    - ambienceTags (list of strings): e.g., "quiet", "live_music", "outdoor_seating".
    - occasionTags (list of strings): e.g., "date_night", "family_dinner", "business_lunch".
    - servesAlcohol (boolean): true if user mentions alcohol, bar, or cocktails.
    
    If a field is not mentioned, its value should be null.
    Respond with ONLY the JSON object, nothing else. Do not use markdown.
    """;

        // 2. Construct the request payload, just like your working example
        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of("parts", new Object[] {
                                Map.of("text", prompt.formatted(userQuery))
                        })
                }
        );

        try {
            // 3. Make the API call using your proven WebClient structure
            String rawResponse = webClient.post()
                    .uri("/v1beta/models/gemini-2.5-flash:generateContent")
                    .header("x-goog-api-key", apiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Block to wait for the synchronous response

            JsonNode root = objectMapper.readTree(rawResponse);
            String content = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            System.out.println("Raw AI Content: " + content);
            String cleanJson = extractJson(content);
            if (cleanJson == null) {
                System.err.println("Could not find a valid JSON object in the AI response.");
                System.err.println("Raw AI Content: " + content);
                return new SearchCriteria(null, null, null, null, null, null, null, null, null);
            }

            return objectMapper.readValue(cleanJson, SearchCriteria.class);

        } catch (Exception e) {
            System.err.println("Error processing Gemini API call: " + e.getMessage());
            return new SearchCriteria(null, null, null, null, null, null, null, null, null);
        }
    }
    private String extractJson(String text) {
        // This regex finds a string that starts with { and ends with }, covering nested brackets.
        Pattern pattern = Pattern.compile("\\{.*\\}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null; // Return null if no JSON object is found
    }

}
