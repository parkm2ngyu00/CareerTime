package kr.ac.dankook.ace.careertime.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String username;
    private String password;
}
