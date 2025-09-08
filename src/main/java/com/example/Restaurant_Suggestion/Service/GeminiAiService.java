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
            You are an expert at extracting restaurant search criteria from a user's query.
            From the query: "%s", extract the cuisine, the maximum price level, and the vibe.
            The price level should be an integer from 1 to 4, where 1 is cheap and 4 is very expensive.
            If a cuisine, price, or vibe is not mentioned, the value for that key should be null.
            Respond with ONLY the JSON object, nothing else. Do not use markdown.
            Example: {"cuisine": "Cafe", "maxPriceLevel": 2, "vibe": "casual"}
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

            return objectMapper.readValue(content, SearchCriteria.class);

        } catch (Exception e) {
            System.err.println("Error processing Gemini API call: " + e.getMessage());
            // Return empty criteria so the app doesn't crash
            return new SearchCriteria(null, null, null);
        }
    }

}
