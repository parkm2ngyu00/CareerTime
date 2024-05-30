package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setPassword("password");
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");
        testUser.setUser_type("USER");
        userRepository.save(testUser);
    }

    @Test
    void createProfile() {
        // Given
        String companyName = "Test Company";
        String position = "Developer";
        List<String> hashtags = Arrays.asList("#java", "#spring");
        String introduction = "Hello, I'm a developer.";
        String base64Image = Base64.getEncoder().encodeToString("test image content".getBytes());

        // When
        Profile createdProfile = profileService.createProfile(testUser.getUser_id(), companyName, position, hashtags, introduction, base64Image);

        // Then
        assertNotNull(createdProfile);
        assertEquals(companyName, createdProfile.getCompany_name());
        assertEquals(position, createdProfile.getPosition());
        assertEquals(introduction, createdProfile.getIntroduction());
        assertEquals("#java, #spring", createdProfile.getHashtags());
        assertEquals(testUser.getUser_id(), createdProfile.getUser().getUser_id());
        assertNotNull(createdProfile.getProfilePicture());

        // Verify the profile is saved in the database
        Optional<Profile> foundProfile = profileRepository.findById(createdProfile.getProfile_id());
        assertTrue(foundProfile.isPresent());
        assertEquals(companyName, foundProfile.get().getCompany_name());
    }

    @Test
    void findProfileByUserId() {
        // Given
        Profile profile = new Profile();
        profile.setUser(testUser);
        profile.setCompany_name("Test Company");
        profile.setPosition("Developer");
        profile.setHashtags("#java, #spring");
        profile.setIntroduction("Hello, I'm a developer.");
        profileRepository.save(profile);

        // When
        Profile foundProfile = profileService.findProfileByUserId(testUser.getUser_id());

        // Then
        assertNotNull(foundProfile);
        assertEquals(testUser.getUser_id(), foundProfile.getUser().getUser_id());
        assertEquals("Test Company", foundProfile.getCompany_name());
        assertEquals("Developer", foundProfile.getPosition());
    }

    @Test
    void updateProfile() {
        // Given
        Profile profile = new Profile();
        profile.setUser(testUser);
        profile.setCompany_name("Test Company");
        profile.setPosition("Developer");
        profile.setHashtags("#java, #spring");
        profile.setIntroduction("Hello, I'm a developer.");
        profileRepository.save(profile);

        Long profileId = profile.getProfile_id();
        Profile profileDetails = new Profile();
        profileDetails.setCompany_name("Updated Company");
        profileDetails.setPosition("Senior Developer");
        profileDetails.setHashtags("#updated, #java, #spring");
        profileDetails.setIntroduction("Hello, I'm a senior developer.");
        String updatedBase64Image = Base64.getEncoder().encodeToString("updated image content".getBytes());

        // When
        Profile updatedProfile = profileService.updateProfile(profileId, profileDetails, updatedBase64Image);

        // Then
        assertNotNull(updatedProfile);
        assertEquals("Updated Company", updatedProfile.getCompany_name());
        assertEquals("Senior Developer", updatedProfile.getPosition());
        assertEquals("#updated, #java, #spring", updatedProfile.getHashtags());
        assertEquals("Hello, I'm a senior developer.", updatedProfile.getIntroduction());
        assertNotNull(updatedProfile.getProfilePicture());
    }

    @Test
    void deleteProfile() {
        // Given
        Profile profile = new Profile();
        profile.setUser(testUser);
        profile.setCompany_name("Test Company");
        profile.setPosition("Developer");
        profile.setHashtags("#java, #spring");
        profile.setIntroduction("Hello, I'm a developer.");
        profileRepository.save(profile);

        Long profileId = profile.getProfile_id();

        // When
        profileService.deleteProfile(profileId);

        // Then
        Optional<Profile> foundProfile = profileRepository.findById(profileId);
        assertFalse(foundProfile.isPresent());
    }
}