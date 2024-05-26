package kr.ac.dankook.ace.careertime.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProfileRequest {
    private String companyName;
    private String position;
    private List<String> hashtags;
    private String introduction;
}
