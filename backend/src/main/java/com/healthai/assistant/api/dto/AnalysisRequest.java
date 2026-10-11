package com.healthai.assistant.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record AnalysisRequest(
        @NotEmpty(message = "Select at least one symptom.")
        @Size(max = 30, message = "Select no more than 30 symptoms.")
        List<@Size(max = 80, message = "Each symptom must be 80 characters or fewer.") String> symptoms,
        @Size(max = 50, message = "Duration must be 50 characters or fewer.")
        String duration,
        @Size(max = 20, message = "Severity must be 20 characters or fewer.")
        String severity,
        @Size(max = 2000, message = "Additional details must be 2000 characters or fewer.")
        String notes) {
}
