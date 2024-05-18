package kr.ac.dankook.ace.careertime.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class HashTagService {

    public List<String> extractHashtags(String content) {
        List<String> hashtags = new ArrayList<>();
        Matcher matcher = Pattern.compile("#([\\p{L}\\p{IsHangul}\\p{Digit}]+)").matcher(content);
        while (matcher.find()) {
            hashtags.add(matcher.group(1));
        }
        return hashtags;
    }
}