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

@Service
public class GeminiAiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final String geminiApiPath = "/v1beta/models/gemini-pro:generateContent";

    @Autowired
    private WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public GeminiAiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public String testGemini() {
        String question = "Explain how AI works in a few words";

        String requestBody = """
        {
            "contents": [{
                "parts": [{"text": "%s"}]
            }]
        }
        """.formatted(question);

        try {
            // WebClient works with "reactive streams" (Mono for a single result).
            // We build the request, retrieve the response, and convert the body to a String.
            Mono<String> responseMono = webClient.post()
                    .uri(uriBuilder -> uriBuilder.path(geminiApiPath).queryParam("key", apiKey).build())
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class);

            // For this simple case, we use .block() to wait for the response and get the result.
            String response = responseMono.block();

            // Extract the text content from the response
            JsonNode root = objectMapper.readTree(response);
            return root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

        } catch (Exception e) {
            System.err.println("Error in testGemini call: " + e.getMessage());
            return "Error: Could not get a response from Gemini. Check the console logs.";
        }
    }
//    public SearchCriteria getSearchCriteria(String query) {
//        String prompt = """
//            You are an expert at extracting restaurant search criteria from a user's query.
//            From the query: "%s", extract the cuisine, the maximum price level, and the vibe.
//            The price level should be an integer from 1 to 4, where 1 is cheap and 4 is very expensive.
//            If a cuisine, price, or vibe is not mentioned, the value for that key should be null.
//            Respond with ONLY the JSON object, nothing else.
//            Example: {"cuisine": "Cafe", "maxPriceLevel": 2, "vibe": "casual"}
//            """;
//
//        String requestBody = """
//        {
//            "contents": [{
//                "parts": [{"text": "%s"}]
//            }]
//        }
//        """.formatted(prompt.formatted(query));
//
//        // 3. Set the necessary HTTP headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // 4. Create the full HTTP request with body and headers
//        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
//        String url = apiUrl + "?key=" + apiKey;
//
//        try {
//            // 5. Make the API call
//            String response = restTemplate.postForObject(url, request, String.class);
//
//            // 6. Manually parse the nested JSON response to get the AI's text
//            JsonNode root = objectMapper.readTree(response);
//            String content = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
//
//            // 7. Parse that text (which is a JSON string) into our SearchCriteria object
//            return objectMapper.readValue(content, SearchCriteria.class);
//
//        } catch (JsonProcessingException e) {
//            System.err.println("Error parsing JSON from Gemini: " + e.getMessage());
//            return new SearchCriteria(null, null, null);
//        } catch (Exception e) {
//            System.err.println("Error calling Gemini API: " + e.getMessage());
//            return new SearchCriteria(null, null, null);
//        }
//
//    }

}
