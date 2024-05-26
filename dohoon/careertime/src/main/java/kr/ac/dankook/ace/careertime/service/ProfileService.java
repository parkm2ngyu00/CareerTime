package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public Profile createProfile(Long userId, String companyName, String position, List<String> hashtags, String introduction, MultipartFile profilePicture) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        String hashtagString = String.join(", ", hashtags);
        String base64Image = encodeFileToBase64(profilePicture);

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setCompany_name(companyName);
        profile.setPosition(position);
        profile.setIntroduction(introduction);
        profile.setHashtags(hashtagString);
        profile.setProfilePicture(base64Image);

        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long id, Profile profileDetails, MultipartFile profilePicture) {
        return profileRepository.findById(id)
                .map(profile -> {
                    profile.setCompany_name(profileDetails.getCompany_name());
                    profile.setPosition(profileDetails.getPosition());
                    profile.setIntroduction(profileDetails.getIntroduction());
                    profile.setHashtags(profileDetails.getHashtags());
                    if (profilePicture != null && !profilePicture.isEmpty()) {
                        String base64Image = encodeFileToBase64(profilePicture);
                        profile.setProfilePicture(base64Image);
                    }
                    return profileRepository.save(profile);
                }).orElseThrow(() -> new RuntimeException("Profile not found with id " + id));
    }

    private String encodeFileToBase64(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to encode file to Base64", e);
        }
    }

    public Profile findProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        return profileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));
    }

    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }
}
