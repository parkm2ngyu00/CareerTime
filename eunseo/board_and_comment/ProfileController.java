package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.dto.ProfileRequest;
import kr.ac.dankook.ace.careertime.dto.ProfileResponse;
import kr.ac.dankook.ace.careertime.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{userId}")
    public ResponseEntity<ProfileResponse> createProfile(@PathVariable Long userId,
                                                         @RequestBody ProfileRequest profileRequest) {
        ProfileResponse profileResponse = profileService.createProfile(
                userId,
                profileRequest.getCompanyName(),
                profileRequest.getPosition(),
                profileRequest.getHashtags(),
                profileRequest.getIntroduction(),
                profileRequest.getProfilePicture()
        );
        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long userId) {
        ProfileResponse profileResponse = profileService.findProfileByUserId(userId);
        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ProfileResponse> updateProfile(@PathVariable Long userId,
                                                         @RequestBody ProfileRequest profileRequest) {
        ProfileResponse profileResponse = profileService.updateProfileByUserId(
                userId,
                profileRequest.getCompanyName(),
                profileRequest.getPosition(),
                profileRequest.getHashtags(),
                profileRequest.getIntroduction(),
                profileRequest.getProfilePicture()
        );
        return ResponseEntity.ok(profileResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long userId) {
        profileService.deleteProfileByUserId(userId);
        return ResponseEntity.noContent().build();
    }
}
