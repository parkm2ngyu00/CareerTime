package kr.ac.dankook.ace.careertime.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long user_id;
    private String username;
    private String name;
    private String email;
    private String user_type;
    private Long points;
    private String join_date;
}
