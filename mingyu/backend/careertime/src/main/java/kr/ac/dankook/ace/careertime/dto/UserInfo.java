package kr.ac.dankook.ace.careertime.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {
    private String username;
    private String usercompany;
    private String useremail;
    private List<String> userinterest;
}
