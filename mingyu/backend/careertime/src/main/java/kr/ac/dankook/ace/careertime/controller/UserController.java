package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.UserRequest;
import kr.ac.dankook.ace.careertime.dto.UserResponse;
import kr.ac.dankook.ace.careertime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) {
        User registeredUser = userService.registerUser(
                userRequest.getUsername(),
                userRequest.getPassword(),
                userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getUser_type());

        UserResponse userResponse = mapToUserResponse(registeredUser);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequest userRequest) {
        Optional<User> loginUser = userService.loginUser(userRequest.getUsername(), userRequest.getPassword());
        if (loginUser.isPresent()) {
            UserResponse userResponse = mapToUserResponse(loginUser.get());
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUser_id(user.getUser_id());
        userResponse.setUsername(user.getUsername());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setUser_type(user.getUser_type());
        userResponse.setPoints(user.getPoints());
        userResponse.setJoin_date(user.getJoin_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return userResponse;
    }
}
