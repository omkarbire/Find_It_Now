package com.finditnow.user.service;

import com.finditnow.user.dto.UserProfileRequest;
import com.finditnow.user.dto.UserProfileResponse;
import com.finditnow.user.entity.UserProfile;
import com.finditnow.user.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserProfileResponse upsertProfile(String username, UserProfileRequest request) {
        UserProfile profile = repository.findByUsername(username)
                .orElseGet(() -> UserProfile.builder().username(username).build());

        profile.setFullName(request.getFullName());
        profile.setPhone(request.getPhone());
        profile.setAddressLine1(request.getAddressLine1());
        profile.setAddressLine2(request.getAddressLine2());
        profile.setCity(request.getCity());
        profile.setState(request.getState());
        profile.setPincode(request.getPincode());
        profile.setLatitude(request.getLatitude());
        profile.setLongitude(request.getLongitude());

        UserProfile saved = repository.save(profile);
        return toResponse(saved);
    }

    public UserProfileResponse getProfile(String username) {
        UserProfile profile = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Profile not found for user: " + username));
        return toResponse(profile);
    }

    private UserProfileResponse toResponse(UserProfile profile) {
        return UserProfileResponse.builder()
                .id(profile.getId())
                .username(profile.getUsername())
                .fullName(profile.getFullName())
                .phone(profile.getPhone())
                .addressLine1(profile.getAddressLine1())
                .addressLine2(profile.getAddressLine2())
                .city(profile.getCity())
                .state(profile.getState())
                .pincode(profile.getPincode())
                .latitude(profile.getLatitude())
                .longitude(profile.getLongitude())
                .build();
    }
}
