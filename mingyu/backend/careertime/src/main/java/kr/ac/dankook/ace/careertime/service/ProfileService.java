package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.ProfileResponse;
import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    // Profile 객체 대신 ProfileResponse 객체를 반환하도록 변경
    public ProfileResponse createProfile(Long userId, String companyName, String position, List<String> hashtags, String introduction, String profilePicture) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        String hashtagString = String.join(", ", hashtags);

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setCompany_name(companyName);
        profile.setPosition(position);
        profile.setIntroduction(introduction);
        profile.setHashtags(hashtagString);
        profile.setProfilePicture(profilePicture);

        Profile savedProfile = profileRepository.save(profile);
        return mapToProfileResponse(savedProfile); // ProfileResponse로 매핑
    }

    public ProfileResponse updateProfile(Long id, Profile profileDetails, String profilePicture) {
        Profile updatedProfile = profileRepository.findById(id)
                .map(profile -> {
                    profile.setCompany_name(profileDetails.getCompany_name());
                    profile.setPosition(profileDetails.getPosition());
                    profile.setIntroduction(profileDetails.getIntroduction());
                    profile.setHashtags(profileDetails.getHashtags());
                    if (profilePicture != null && !profilePicture.isEmpty()) {
                        profile.setProfilePicture(profilePicture);
                    }
                    return profileRepository.save(profile);
                }).orElseThrow(() -> new RuntimeException("Profile not found with id " + id));
        return mapToProfileResponse(updatedProfile); // ProfileResponse로 매핑
    }

    public ProfileResponse updateProfileByUserId(Long userId, Profile profileDetails, String profilePicture) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));

        profile.setCompany_name(profileDetails.getCompany_name());
        profile.setPosition(profileDetails.getPosition());
        profile.setIntroduction(profileDetails.getIntroduction());
        profile.setHashtags(profileDetails.getHashtags());
        if (profilePicture != null && !profilePicture.isEmpty()) {
            profile.setProfilePicture(profilePicture);
        }
        Profile updatedProfile = profileRepository.save(profile);
        return mapToProfileResponse(updatedProfile); // ProfileResponse로 매핑
    }

    public ProfileResponse findProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));
        return mapToProfileResponse(profile); // ProfileResponse로 반환
    }

    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }

    public void deleteProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));
        profileRepository.delete(profile);
    }

    // ProfileResponse로 매핑하는 메서드 추가
    public ProfileResponse mapToProfileResponse(Profile profile) {
        User user = profile.getUser();
        return new ProfileResponse(
                user.getName(),
                profile.getCompany_name(),
                profile.getProfilePicture(),
                user.getEmail(),
                profile.getIntroduction(),
                Arrays.asList(profile.getHashtags().split(", "))
        );
    }

}