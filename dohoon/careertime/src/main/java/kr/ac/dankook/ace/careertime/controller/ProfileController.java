package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.BoardRequest;
import kr.ac.dankook.ace.careertime.dto.ProfileRequest;
import kr.ac.dankook.ace.careertime.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestParam Long userId,
                                                 @RequestBody ProfileRequest profileRequest) {
        Profile createdProfile = profileService.createProfile(
                userId,
                profileRequest.getCompanyName(),
                profileRequest.getPosition(),
                profileRequest.getHashtags(),
                profileRequest.getIntroduction()
        );
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    // Get profile by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<Profile> getProfileByUserId(@PathVariable Long userId) {
        Profile profile = profileService.findProfileByUserId(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // Update a profile
    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long profileId, @RequestBody ProfileRequest profileRequest) {
        Profile updatedProfile = new Profile();
        updatedProfile.setCompany_name(profileRequest.getCompanyName());
        updatedProfile.setPosition(profileRequest.getPosition());
        updatedProfile.setIntroduction(profileRequest.getIntroduction());
        updatedProfile.setHashtags(String.join(", ", profileRequest.getHashtags()));

        Profile profile = profileService.updateProfile(profileId, updatedProfile);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    // Delete a profile
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long profileId) {
        profileService.deleteProfile(profileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
