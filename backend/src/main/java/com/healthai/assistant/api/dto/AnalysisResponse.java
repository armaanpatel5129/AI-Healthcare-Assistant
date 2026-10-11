package com.healthai.assistant.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AnalysisResponse(
        Long id,
        List<String> symptoms,
        String duration,
        String severity,
        String notes,
        String riskLevel,
        String guidance,
        String warning,
        LocalDateTime createdAt) {
}
