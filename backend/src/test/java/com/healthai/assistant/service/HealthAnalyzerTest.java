package com.healthai.assistant.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HealthAnalyzerTest {
    private final HealthAnalyzer analyzer = new HealthAnalyzer();

    @Test
    void flagsEmergencySymptomsBeforeOtherRules() {
        assertEquals("EMERGENCY", analyzer.analyze("mild chest discomfort", "1 day", "MILD").riskLevel());
    }

    @Test
    void flagsModerateRiskForSevereSymptomsAndLongDuration() {
        assertEquals("MODERATE", analyzer.analyze("headache", "1 day", "SEVERE").riskLevel());
        assertEquals("MODERATE", analyzer.analyze("cough", "1 week", "MILD").riskLevel());
    }

    @Test
    void returnsGeneralGuidanceForShortMildSymptoms() {
        assertEquals("LOW", analyzer.analyze("headache", "1 day", "MILD").riskLevel());
    }

    @Test
    void treatsUnparseableDurationAsUnknown() {
        assertEquals("LOW", analyzer.analyze("headache", "unknown", "MILD").riskLevel());
    }
}
