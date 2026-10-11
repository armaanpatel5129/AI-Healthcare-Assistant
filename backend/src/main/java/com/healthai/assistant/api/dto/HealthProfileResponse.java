package com.healthai.assistant.api.dto;

public record HealthProfileResponse(
        String name,
        String email,
        Integer age,
        String gender,
        String blood,
        java.math.BigDecimal height,
        java.math.BigDecimal weight,
        String allergies,
        String conditions) {
}
