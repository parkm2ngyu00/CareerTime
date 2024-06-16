package kr.ac.dankook.ace.careertime.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private Long user_id;
    private String username;
    private String usercompany;
    private String useremail;
    private String userimage;
    private List<String> userinterest;
}
