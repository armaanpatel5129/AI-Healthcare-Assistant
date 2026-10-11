package com.healthai.assistant.service;

import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Component;

@Component
public class HealthAnalyzer {
    private static final List<String> EMERGENCY_SYMPTOMS = List.of(
            "chest pain", "chest discomfort", "difficulty breathing", "shortness of breath",
            "cannot breathe", "unconscious", "severe bleeding", "seizure", "stroke");
    private static final List<String> MODERATE_SYMPTOMS = List.of(
            "high fever", "vomiting", "blood", "persistent");

    public Result analyze(String symptoms, String duration, String severity) {
        String text = (symptoms == null ? "" : symptoms).toLowerCase(Locale.ROOT);
        String normalizedSeverity = severity == null ? "" : severity.toUpperCase(Locale.ROOT);

        if (EMERGENCY_SYMPTOMS.stream().anyMatch(text::contains)) {
            return new Result("EMERGENCY",
                    "These symptoms can be serious. Seek immediate emergency medical care.",
                    "Call your local emergency number or go to the nearest hospital now.");
        }
        if ("SEVERE".equals(normalizedSeverity)
                || durationInDays(duration) >= 3
                || MODERATE_SYMPTOMS.stream().anyMatch(text::contains)) {
            return new Result("MODERATE",
                    "This symptom pattern may need medical attention. Rest, drink fluids, and consult a healthcare professional.",
                    "Please see a doctor soon, especially if symptoms get worse.");
        }
        return new Result("LOW",
                "Rest, drink enough water, and monitor your symptoms.",
                "If symptoms continue or worsen, consult a healthcare professional.");
    }

    private int durationInDays(String duration) {
        if (duration == null) {
            return 0;
        }
        String normalized = duration.toLowerCase(Locale.ROOT);
        String digits = normalized.replaceAll("[^0-9]", "");
        if (digits.isEmpty()) {
            return 0;
        }
        try {
            int amount = Integer.parseInt(digits);
            if (normalized.contains("week")) {
                return Math.multiplyExact(amount, 7);
            }
            if (normalized.contains("month")) {
                return Math.multiplyExact(amount, 30);
            }
            return amount;
        } catch (ArithmeticException | NumberFormatException exception) {
            return Integer.MAX_VALUE;
        }
    }

    public record Result(String riskLevel, String guidance, String warning) {
    }
}
