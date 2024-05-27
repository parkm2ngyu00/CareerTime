package kr.ac.dankook.ace.careertime.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String password;
    private String name;
    private String email;
    private String user_type;
}
