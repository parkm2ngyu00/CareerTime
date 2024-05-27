package kr.ac.dankook.ace.careertime.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProfileRequest {
    private String companyName;
    private String position;
    private List<String> hashtags;
    private String introduction;
    private String profilePicture;
}