package kr.ac.dankook.ace.careertime.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private Long user_id;
    private Long comment_id;
    private Long comment_rate;
    private String comment_text;
}
