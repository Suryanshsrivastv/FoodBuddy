package com.example.Restaurant_Suggestion.Controller;

import com.example.Restaurant_Suggestion.Service.FeedService;
import com.example.Restaurant_Suggestion.dto.RankedRestaurant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @GetMapping("/feed")
    public ResponseEntity<List<RankedRestaurant>> getPersonalizedFeed() {
        return ResponseEntity.ok(feedService.generatePersonalizedFeed());
    }
}
