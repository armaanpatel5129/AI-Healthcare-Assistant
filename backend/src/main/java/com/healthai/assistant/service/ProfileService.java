package com.healthai.assistant.service;

import com.healthai.assistant.api.dto.HealthProfileRequest;
import com.healthai.assistant.api.dto.HealthProfileResponse;
import com.healthai.assistant.model.HealthProfileEntity;
import com.healthai.assistant.model.UserEntity;
import com.healthai.assistant.repository.HealthProfileRepository;
import com.healthai.assistant.repository.UserRepository;
import com.healthai.assistant.web.ApiException;
import java.math.BigDecimal;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {
    private final UserRepository userRepository;
    private final HealthProfileRepository profileRepository;

    public ProfileService(UserRepository userRepository, HealthProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional(readOnly = true)
    public HealthProfileResponse get(long userId) {
        UserEntity user = findUser(userId);
        HealthProfileEntity profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException(404, "Health profile not found."));
        return toResponse(user, profile);
    }

    @Transactional
    public HealthProfileResponse save(long userId, HealthProfileRequest request) {
        UserEntity user = findUser(userId);
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        userRepository.findByEmailIgnoreCase(email)
                .filter(existing -> !existing.getId().equals(userId))
                .ifPresent(existing -> {
                    throw new ApiException(409, "That email address is already in use.");
                });

        user.setFullName(request.name().trim());
        user.setEmail(email);
        HealthProfileEntity profile = profileRepository.findByUserId(userId)
                .orElseGet(() -> new HealthProfileEntity(userId));
        profile.setAge(request.age());
        profile.setGender(blankToNull(request.gender()));
        profile.setBloodGroup(blankToNull(request.blood()));
        profile.setHeightCm(toDecimal(request.height()));
        profile.setWeightKg(toDecimal(request.weight()));
        profile.setAllergies(blankToNull(request.allergies()));
        profile.setExistingConditions(blankToNull(request.conditions()));
        userRepository.save(user);
        profileRepository.save(profile);
        return toResponse(user, profile);
    }

    private UserEntity findUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(401, "This account is no longer available."));
    }

    private HealthProfileResponse toResponse(UserEntity user, HealthProfileEntity profile) {
        return new HealthProfileResponse(
                user.getFullName(), user.getEmail(), profile.getAge(), profile.getGender(),
                profile.getBloodGroup(), profile.getHeightCm(), profile.getWeightKg(),
                profile.getAllergies(), profile.getExistingConditions());
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private BigDecimal toDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }
}
