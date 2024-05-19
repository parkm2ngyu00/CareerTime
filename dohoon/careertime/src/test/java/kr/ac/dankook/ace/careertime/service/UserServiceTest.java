package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void registerUser() {
        String username = "testUser";
        String password = "testPass";
        String name = "Test Name";
        String email = "test@example.com";
        String userType = "NORMAL";

        User newUser = userService.registerUser(username, password, name, email, userType);

        assertThat(newUser).isNotNull();
        assertThat(newUser.getUsername()).isEqualTo(username);
        assertThat(newUser.getName()).isEqualTo(name);
        assertThat(newUser.getEmail()).isEqualTo(email);
        assertThat(newUser.getUser_type()).isEqualTo(userType);
        assertThat(passwordEncoder.matches(password, newUser.getPassword())).isTrue();
    }

    @Test
    void findUserById() {
        String username = "findUser";
        String password = "password";
        String name = "Find User";
        String email = "finduser@example.com";
        String userType = "NORMAL";

        User newUser = userService.registerUser(username, password, name, email, userType);
        User foundUser = userService.findUserById(newUser.getUser_id());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUser_id()).isEqualTo(newUser.getUser_id());
    }

    @Test
    void updateUser() {
        String username = "updateUser";
        String password = "password";
        String name = "Update User";
        String email = "updateuser@example.com";
        String userType = "NORMAL";
        User newUser = userService.registerUser(username, password, name, email, userType);

        String updatedUsername = "updatedUser";
        String updatedPassword = "newPassword";
        String updatedName = "Updated Name";
        String updatedEmail = "updated@example.com";
        String updatedUserType = "ADMIN";

        User updatedUser = userService.updateUser(newUser.getUser_id(), updatedUsername, updatedPassword, updatedName, updatedEmail, updatedUserType);

        assertThat(updatedUser.getUsername()).isEqualTo(updatedUsername);
        assertThat(passwordEncoder.matches(updatedPassword, updatedUser.getPassword())).isTrue();
        assertThat(updatedUser.getName()).isEqualTo(updatedName);
        assertThat(updatedUser.getEmail()).isEqualTo(updatedEmail);
        assertThat(updatedUser.getUser_type()).isEqualTo(updatedUserType);
    }

    @Test
    void deleteUser() {
        String username = "deleteUser";
        String password = "password";
        String name = "Delete User";
        String email = "deleteuser@example.com";
        String userType = "NORMAL";

        User newUser = userService.registerUser(username, password, name, email, userType);
        boolean beforeDelete = userRepository.existsById(newUser.getUser_id());
        userService.deleteUser(newUser.getUser_id());
        boolean afterDelete = userRepository.existsById(newUser.getUser_id());

        assertTrue(beforeDelete);
        assertFalse(afterDelete);
    }
}