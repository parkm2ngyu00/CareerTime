package kr.ac.dankook.ace.careertime.controller;

import kr.ac.dankook.ace.careertime.domain.Board;
import kr.ac.dankook.ace.careertime.domain.Comment;
import kr.ac.dankook.ace.careertime.domain.Profile;
import kr.ac.dankook.ace.careertime.domain.User;
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
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        Profile createdProfile = profileService.createProfile(profile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }

    // Get profiles by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Profile>> getProfilesByUserId(@PathVariable Long userId) {
        List<Profile> profiles = profileService.findProfileByUserId(userId);
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }

    // Update a profile
    @PutMapping("/{profileId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long profileId, @RequestBody Profile updatedProfile) {
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
