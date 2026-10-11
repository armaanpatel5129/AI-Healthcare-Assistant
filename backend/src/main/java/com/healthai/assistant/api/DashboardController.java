package com.healthai.assistant.api;

import com.healthai.assistant.security.AuthInterceptor;
import com.healthai.assistant.service.HealthAnalysisService;
import com.healthai.assistant.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final HealthAnalysisService analysisService;
    private final ProfileService profileService;

    public DashboardController(HealthAnalysisService analysisService, ProfileService profileService) {
        this.analysisService = analysisService;
        this.profileService = profileService;
    }

    @GetMapping
    public Map<String, Object> summary(HttpServletRequest request) {
        long userId = (Long) request.getAttribute(AuthInterceptor.USER_ID_ATTRIBUTE);
        var profile = profileService.get(userId);
        return Map.of(
                "analysisCount", analysisService.count(userId),
                "profileSaved", profile.age() != null || profile.gender() != null
                        || profile.blood() != null || profile.height() != null
                        || profile.weight() != null || profile.allergies() != null
                        || profile.conditions() != null);
    }
}
