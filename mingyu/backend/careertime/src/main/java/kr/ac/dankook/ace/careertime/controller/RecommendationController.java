package kr.ac.dankook.ace.careertime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kr.ac.dankook.ace.careertime.service.RecommendationService;
import kr.ac.dankook.ace.careertime.domain.Board;
import java.util.List;

@RestController
@RequestMapping("/api/boards")

public class RecommendationController {

    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<Board>> getRecommendations(@RequestParam Long userId) {
        List<Board> recommendations = recommendationService.recommendBoardsForUser(userId);
        if (recommendations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(recommendations);
    }
}