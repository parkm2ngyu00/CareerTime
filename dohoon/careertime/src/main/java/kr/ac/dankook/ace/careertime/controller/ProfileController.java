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

    @PostMapping("/{userId}")
    public ResponseEntity<ProfileResponse> createProfile(@PathVariable("userId") Long userId,
                                                         @RequestBody ProfileRequest profileRequest) {
        // ProfileResponse 객체를 직접 반환하도록 수정
        ProfileResponse createdProfile = profileService.createProfile(
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
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfileByUserId(@PathVariable("userId") Long userId) {
        ProfileResponse userProfileResponse = profileService.findProfileByUserId(userId);
        return new ResponseEntity<>(userProfileResponse, HttpStatus.OK);
    }

    // Update a profile by userId
    @PutMapping("/{userId}")
    public ResponseEntity<ProfileResponse> updateProfileByUserId(@PathVariable("userId") Long userId,
                                                                 @RequestBody ProfileRequest profileRequest) {
        Profile profileDetails = new Profile();
        profileDetails.setCompany_name(profileRequest.getCompanyName());
        profileDetails.setPosition(profileRequest.getPosition());
        profileDetails.setIntroduction(profileRequest.getIntroduction());
        profileDetails.setHashtags(String.join(", ", profileRequest.getHashtags()));

        ProfileResponse updatedProfile = profileService.updateProfileByUserId(userId, profileDetails, profileRequest.getProfilePicture());
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }

    // Delete a profile by userId
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteProfileByUserId(@PathVariable("userId") Long userId) {
        profileService.deleteProfileByUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}