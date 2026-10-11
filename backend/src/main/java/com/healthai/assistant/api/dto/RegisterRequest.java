package com.healthai.assistant.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Full name is required.")
        @Size(max = 100, message = "Full name must be 100 characters or fewer.")
        String name,
        @NotBlank(message = "Email is required.")
        @Email(message = "Enter a valid email address.")
        @Size(max = 150, message = "Email must be 150 characters or fewer.")
        String email,
        @NotBlank(message = "Password is required.")
        @Size(min = 6, max = 72, message = "Password must be between 6 and 72 characters.")
        String password) {
}
