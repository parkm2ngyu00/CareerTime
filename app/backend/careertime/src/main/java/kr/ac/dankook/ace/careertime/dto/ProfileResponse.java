package kr.ac.dankook.ace.careertime.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ProfileResponse {
    private String userName;
    private String userCompany;
    private String userImg;
    private String userEmail;
    private String userIntroduction;
    private List<String> userInterest;
}
