package kr.ac.dankook.ace.careertime.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardResponse {
    private Long post_id;
    private String title;
    private List<String> hashtags;
    private String content;
    private String postdate;
    private UserInfo userinfo;
}