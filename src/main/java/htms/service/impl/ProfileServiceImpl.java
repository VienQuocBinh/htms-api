package htms.service.impl;

import htms.api.request.ProfileRequest;
import htms.api.request.ProfileUpdateRequest;
import htms.api.response.ProfileResponse;
import htms.model.Profile;
import htms.repository.ProfileRepository;
import htms.service.ProfileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@EnableAsync
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    @Override
    @Async
    public void updateProfile(ProfileUpdateRequest request) {
        // todo: handle exception
        var profile = profileRepository.findById(request.getId())
                .orElseThrow();
        profile.setStatus(request.getStatus());
        profileRepository.save(profile);
//        return modelMapper.map(profile, ProfileResponse.class);
    }

    @Override
    @Transactional
    public List<ProfileResponse> createProfiles(List<ProfileRequest> request) {
        // todo: assign created by account
        List<Profile> list = new ArrayList<>();
        for (ProfileRequest profileRequest : request) {
            list.add(Profile.builder()
                    .status(profileRequest.getStatus())
                    .createdBy(UUID.randomUUID())
                    .build());
        }
        return profileRepository.saveAll(list)
                .parallelStream()
                .map((element) -> modelMapper.map(
                        element,
                        ProfileResponse.class)).toList();
    }
}
