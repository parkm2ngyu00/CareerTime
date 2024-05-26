package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getUser_type());
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        Optional<User> loginUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (loginUser.isPresent()) {
            return ResponseEntity.ok(loginUser.get());
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}
