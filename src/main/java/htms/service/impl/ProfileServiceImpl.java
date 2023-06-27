package htms.service.impl;

import htms.api.request.ProfileUpdateRequest;
import htms.api.response.ProfileResponse;
import htms.repository.ProfileRepository;
import htms.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProfileResponse updateProfile(ProfileUpdateRequest request) {
        // todo: handle exception
        var profile = profileRepository.findById(request.getId())
                .orElseThrow();
        profile.setStatus(request.getStatus());
        profileRepository.save(profile);
        return modelMapper.map(profile, ProfileResponse.class);
    }
}
