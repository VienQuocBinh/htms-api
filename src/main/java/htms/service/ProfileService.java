package htms.service;

import htms.api.request.ProfileRequest;
import htms.api.request.ProfileUpdateRequest;
import htms.api.response.ProfileResponse;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ProfileService {
    ProfileResponse updateProfile(ProfileUpdateRequest request);

    @Transactional
    List<ProfileResponse> createProfiles(List<ProfileRequest> request);
}
