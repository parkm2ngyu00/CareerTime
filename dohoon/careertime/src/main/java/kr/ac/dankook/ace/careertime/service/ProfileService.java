package kr.ac.dankook.ace.careertime.service;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import kr.ac.dankook.ace.careertime.config.ResourceNotFoundException;
import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.repository.CommentRepository;
import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import kr.ac.dankook.ace.careertime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public Profile createProfile(Long userId, String companyName, String position, List<String> hashtags, String introduction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        String hashtagString = String.join(", ", hashtags);

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setCompany_name(companyName);
        profile.setIntroduction(introduction);
        profile.setPosition(position);
        profile.setIntroduction(introduction);
        profile.setHashtags(hashtagString);

        return profileRepository.save(profile);
    }

    public Profile findProfileByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));
        return profileRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Profile not found for user ID: " + userId));
    }

    public Profile updateProfile(Long id, Profile profileDetails) {
        return profileRepository.findById(id)
                .map(profile -> {
                    profile.setCompany_name(profileDetails.getCompany_name());
                    profile.setPosition(profileDetails.getPosition());
                    profile.setIntroduction(profileDetails.getIntroduction());
                    profile.setHashtags(profileDetails.getHashtags());
                    return profileRepository.save(profile);
                }).orElseThrow(() -> new RuntimeException("Profile not found with id " + id));
    }

    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }
}
