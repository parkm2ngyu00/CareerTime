package kr.ac.dankook.ace.careertime.service;

import kr.ac.dankook.ace.careertime.config.ResourceNotFoundException;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.repository.CommentRepository;
import kr.ac.dankook.ace.careertime.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public List<Profile> findProfileByUserId(Long userId) {
        return profileRepository.findAll()
                .stream()
                .filter(p -> p.getUser().getUser_id().equals(userId))
                .collect(Collectors.toList());
    }

    public Profile updateProfile(Long id, Profile profileDetails) {
        return profileRepository.findById(id)
                .map(profile -> {
                    profile.setCompany_name(profileDetails.getCompany_name());
                    profile.setPosition(profileDetails.getPosition());
                    profile.setIntroduction(profileDetails.getIntroduction());
                    return profileRepository.save(profile);
                }).orElseThrow(() -> new RuntimeException("Profile not found with id " + id)); // 적절한 예외 처리를 해주세요.
    }

    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }
}
