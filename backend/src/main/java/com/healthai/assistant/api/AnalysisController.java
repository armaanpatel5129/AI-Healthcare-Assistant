package com.healthai.assistant.api;

import com.healthai.assistant.api.dto.AnalysisRequest;
import com.healthai.assistant.api.dto.AnalysisResponse;
import com.healthai.assistant.security.AuthInterceptor;
import com.healthai.assistant.service.HealthAnalysisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analyses")
public class AnalysisController {
    private final HealthAnalysisService analysisService;

    public AnalysisController(HealthAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping
    public AnalysisResponse analyze(HttpServletRequest request, @Valid @RequestBody AnalysisRequest body) {
        return analysisService.analyze(
                (Long) request.getAttribute(AuthInterceptor.USER_ID_ATTRIBUTE), body);
    }

    @GetMapping
    public List<AnalysisResponse> history(HttpServletRequest request) {
        return analysisService.history((Long) request.getAttribute(AuthInterceptor.USER_ID_ATTRIBUTE));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearHistory(HttpServletRequest request) {
        analysisService.clearHistory((Long) request.getAttribute(AuthInterceptor.USER_ID_ATTRIBUTE));
        return ResponseEntity.noContent().build();
    }
}
