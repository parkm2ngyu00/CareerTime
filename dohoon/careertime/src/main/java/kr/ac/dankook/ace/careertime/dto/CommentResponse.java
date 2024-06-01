package kr.ac.dankook.ace.careertime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {
    private Long comment_id;
    private String username;
    private String useremail;
    private String name;
    private String user_type;
    private Long user_id;
    private int rating;
    private String comment;
}
