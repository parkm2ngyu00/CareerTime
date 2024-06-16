package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.UserResponse;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String name, String email, String userType) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setUser_type(userType);
        return userRepository.save(newUser);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public UserResponse mapToUserResponse(User user) {
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
