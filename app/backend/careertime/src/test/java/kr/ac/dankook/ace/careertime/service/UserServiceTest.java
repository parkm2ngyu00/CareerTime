package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registerUser() {
        // Given
        String username = "newuser";
        String password = "password123";
        String name = "John Doe";
        String email = "john.doe@example.com";
        String userType = "USER";

        // When
        User registeredUser = userService.registerUser(username, password, name, email, userType);

        // Then
        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        assertTrue(passwordEncoder.matches(password, registeredUser.getPassword()));
        assertEquals(name, registeredUser.getName());
        assertEquals(email, registeredUser.getEmail());
        assertEquals(userType, registeredUser.getUser_type());

        // Verify the user is saved in the database
        Optional<User> foundUser = userRepository.findByUsername(username);
        assertTrue(foundUser.isPresent());
        assertEquals(username, foundUser.get().getUsername());
    }

    @Test
    void loginUser() {
        // Given
        String username = "existinguser";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setUser_type("USER");

        userRepository.save(user);

        // When
        Optional<User> loginUser = userService.loginUser(username, password);

        // Then
        assertTrue(loginUser.isPresent());
        assertEquals(username, loginUser.get().getUsername());
    }
}