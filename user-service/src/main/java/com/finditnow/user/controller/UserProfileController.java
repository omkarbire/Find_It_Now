package com.finditnow.user.controller;

import com.finditnow.user.dto.UserProfileRequest;
import com.finditnow.user.dto.UserProfileResponse;
import com.finditnow.user.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    // Create or update current user's profile
    @PostMapping("/me")
    public ResponseEntity<UserProfileResponse> upsertMyProfile(
            @RequestHeader("X-User-Name") String username,
            @Valid @RequestBody UserProfileRequest request
    ) {
        UserProfileResponse response = service.upsertProfile(username, request);
        return ResponseEntity.ok(response);
    }

    // Get current user's profile
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @RequestHeader("X-User-Name") String username
    ) {
        UserProfileResponse response = service.getProfile(username);
        return ResponseEntity.ok(response);
    }
}
