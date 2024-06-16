package kr.ac.dankook.ace.careertime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentSummaryResponse {
    private int reviewCount;
    private double averageRating;
    private List<CommentResponse> reviewList;
}
