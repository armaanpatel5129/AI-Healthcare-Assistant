package com.healthai.assistant.service;

import com.healthai.assistant.api.dto.AuthRequest;
import com.healthai.assistant.api.dto.AuthResponse;
import com.healthai.assistant.api.dto.RegisterRequest;
import com.healthai.assistant.model.HealthProfileEntity;
import com.healthai.assistant.model.UserEntity;
import com.healthai.assistant.repository.HealthProfileRepository;
import com.healthai.assistant.repository.UserRepository;
import com.healthai.assistant.security.TokenService;
import com.healthai.assistant.web.ApiException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final HealthProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository,
                       HealthProfileRepository profileRepository,
                       PasswordEncoder passwordEncoder,
                       TokenService tokenService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (request.password().getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new ApiException(400, "Password must be no more than 72 UTF-8 bytes.");
        }
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ApiException(409, "An account with that email already exists.");
        }

        UserEntity user = userRepository.save(new UserEntity(
                request.name().trim(), email, passwordEncoder.encode(request.password())));
        profileRepository.save(new HealthProfileEntity(user.getId()));
        return toResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest request) {
        if (request.password().getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new ApiException(401, "Email or password is incorrect.");
        }
        UserEntity user = userRepository.findByEmailIgnoreCase(request.email().trim())
                .filter(candidate -> passwordEncoder.matches(request.password(), candidate.getPasswordHash()))
                .orElseThrow(() -> new ApiException(401, "Email or password is incorrect."));
        return toResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse.UserSummary currentUser(long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(401, "This account is no longer available."));
        return new AuthResponse.UserSummary(user.getId(), user.getFullName(), user.getEmail());
    }

    private AuthResponse toResponse(UserEntity user) {
        return new AuthResponse(
                tokenService.createToken(user.getId()),
                new AuthResponse.UserSummary(user.getId(), user.getFullName(), user.getEmail()));
    }
}
