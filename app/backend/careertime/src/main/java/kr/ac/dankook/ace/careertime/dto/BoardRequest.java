package kr.ac.dankook.ace.careertime.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardRequest {
    private String title;
    private List<String> hashtags;
    private String content;
}
