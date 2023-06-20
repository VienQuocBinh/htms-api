package htms.service;

import htms.api.request.ProfileUpdateRequest;
import htms.api.response.ProfileResponse;

public interface ProfileService {
    ProfileResponse updateProfile(ProfileUpdateRequest request);
}
