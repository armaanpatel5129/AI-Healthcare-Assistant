package com.healthai.assistant.api;

import com.healthai.assistant.api.dto.HealthProfileRequest;
import com.healthai.assistant.api.dto.HealthProfileResponse;
import com.healthai.assistant.security.AuthInterceptor;
import com.healthai.assistant.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public HealthProfileResponse get(HttpServletRequest request) {
        return profileService.get((Long) request.getAttribute(AuthInterceptor.USER_ID_ATTRIBUTE));
    }

    @PutMapping
    public HealthProfileResponse save(HttpServletRequest request,
                                      @Valid @RequestBody HealthProfileRequest body) {
        return profileService.save((Long) request.getAttribute(AuthInterceptor.USER_ID_ATTRIBUTE), body);
    }
}
