package com.healthai.assistant.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HealthProfileRequest(
        @NotBlank(message = "Full name is required.")
        @Size(max = 100, message = "Full name must be 100 characters or fewer.")
        String name,
        @NotBlank(message = "Email is required.")
        @Email(message = "Enter a valid email address.")
        @Size(max = 150, message = "Email must be 150 characters or fewer.")
        String email,
        @Min(value = 1, message = "Age must be between 1 and 120.")
        @Max(value = 120, message = "Age must be between 1 and 120.")
        Integer age,
        @Size(max = 20, message = "Gender must be 20 characters or fewer.")
        String gender,
        @Size(max = 10, message = "Blood group must be 10 characters or fewer.")
        String blood,
        @DecimalMin(value = "0.0", inclusive = false, message = "Height must be greater than zero.")
        @DecimalMax(value = "999.99", message = "Height must be 999.99 cm or less.")
        Double height,
        @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than zero.")
        @DecimalMax(value = "999.99", message = "Weight must be 999.99 kg or less.")
        Double weight,
        String allergies,
        String conditions) {
}
