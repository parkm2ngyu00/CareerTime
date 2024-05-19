package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void setup() {
        // 사용자 데이터 생성
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setPassword("testPass");
        testUser.setName("Test Name");
        testUser.setEmail("test@example.com");
        testUser.setUser_type("NORMAL");
        userRepository.save(testUser);
    }

    @Test
    void createProfile() {
        Profile profile = new Profile();
        profile.setUser(testUser);
        profile.setCompany_name("Test Company");
        profile.setPosition("Developer");
        profile.setIntroduction("Hello, I'm a developer.");
        Profile createdProfile = profileService.createProfile(profile);

        assertThat(createdProfile).isNotNull();
        assertThat(createdProfile.getUser()).isEqualTo(testUser);
        assertThat(createdProfile.getCompany_name()).isEqualTo("Test Company");
        assertThat(createdProfile.getPosition()).isEqualTo("Developer");
        assertThat(createdProfile.getIntroduction()).isEqualTo("Hello, I'm a developer.");
    }

    @Test
    void findProfileByUserId() {
        Profile profile = new Profile();
        profile.setUser(testUser);
        profile.setCompany_name("Test Company");
        profile.setPosition("Developer");
        profile.setIntroduction("Hello, I'm a developer.");
        profileRepository.save(profile);

        List<Profile> foundProfiles = profileService.findProfileByUserId(testUser.getUser_id());

        assertThat(foundProfiles).hasSize(1);
        Profile foundProfile = foundProfiles.get(0);
        assertThat(foundProfile.getCompany_name()).isEqualTo("Test Company");
    }

    @Test
    void updateProfile() {
        Profile profile = new Profile();
        profile.setUser(testUser);
        profile.setCompany_name("Test Company");
        profile.setPosition("Developer");
        profile.setIntroduction("Hello, I'm a developer.");
        profile = profileRepository.save(profile);

        Profile newDetails = new Profile();
        newDetails.setCompany_name("Updated Company");
        newDetails.setPosition("Senior Developer");
        newDetails.setIntroduction("Updated Introduction.");

        Profile updatedProfile = profileService.updateProfile(profile.getProfile_id(), newDetails);

        assertThat(updatedProfile.getCompany_name()).isEqualTo("Updated Company");
        assertThat(updatedProfile.getPosition()).isEqualTo("Senior Developer");
        assertThat(updatedProfile.getIntroduction()).isEqualTo("Updated Introduction.");
    }

    @Test
    void deleteProfile() {
        Profile profile = new Profile();
        profile.setUser(testUser);
        profile.setCompany_name("Test Company");
        profile.setPosition("Developer");
        profile.setIntroduction("Hello, I'm a developer.");
        profile = profileRepository.save(profile);

        profileService.deleteProfile(profile.getProfile_id());

        assertTrue(profileRepository.findById(profile.getProfile_id()).isEmpty());
    }
}