package kr.ac.dankook.ace.careertime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {
    private String name;
    private int rating;
    private String comment;
}
