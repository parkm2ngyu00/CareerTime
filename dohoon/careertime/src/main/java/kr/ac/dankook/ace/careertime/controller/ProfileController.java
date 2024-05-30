package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
import kr.ac.dankook.ace.careertime.dto.BoardRequest;
import kr.ac.dankook.ace.careertime.dto.ProfileRequest;
import kr.ac.dankook.ace.careertime.dto.ProfileResponse;
import kr.ac.dankook.ace.careertime.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestParam("userId") Long userId,
                                                 @RequestBody ProfileRequest profileRequest) {
        Profile createdProfile = profileService.createProfile(
                userId,
                profileRequest.getCompanyName(),
                profileRequest.getPosition(),
                profileRequest.getHashtags(),
                profileRequest.getIntroduction(),
                profileRequest.getProfilePicture() // Base64 encoded string
        );
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    // Get profile by userId
    @GetMapping
    public ResponseEntity<ProfileResponse> getProfileByUserId(@RequestParam("userId") Long userId) {
        Profile profile = profileService.findProfileByUserId(userId);
        User user = profile.getUser();

        ProfileResponse userProfileResponse = new ProfileResponse(
                user.getName(),
                profile.getCompany_name(),
                profile.getProfilePicture(),
                user.getEmail(),
                Arrays.asList(profile.getHashtags().split(", "))
        );

        return new ResponseEntity<>(userProfileResponse, HttpStatus.OK);
    }

    // Update a profile
    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable("profileId") Long profileId,
                                                 @RequestBody ProfileRequest profileRequest) {
        Profile profileDetails = new Profile();
        profileDetails.setCompany_name(profileRequest.getCompanyName());
        profileDetails.setPosition(profileRequest.getPosition());
        profileDetails.setIntroduction(profileRequest.getIntroduction());
        profileDetails.setHashtags(String.join(", ", profileRequest.getHashtags()));

        Profile updatedProfile = profileService.updateProfile(profileId, profileDetails, profileRequest.getProfilePicture());
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // Delete a profile
    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable("profileId") Long profileId) {
        profileService.deleteProfile(profileId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}