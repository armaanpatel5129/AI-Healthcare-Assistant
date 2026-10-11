package com.healthai.assistant.service;

import com.healthai.assistant.api.dto.AnalysisRequest;
import com.healthai.assistant.api.dto.AnalysisResponse;
import com.healthai.assistant.model.AnalysisResultEntity;
import com.healthai.assistant.model.SymptomRecordEntity;
import com.healthai.assistant.repository.SymptomRecordRepository;
import com.healthai.assistant.web.ApiException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HealthAnalysisService {
    private final SymptomRecordRepository recordRepository;
    private final HealthAnalyzer analyzer;

    public HealthAnalysisService(SymptomRecordRepository recordRepository, HealthAnalyzer analyzer) {
        this.recordRepository = recordRepository;
        this.analyzer = analyzer;
    }

    @Transactional
    public AnalysisResponse analyze(long userId, AnalysisRequest request) {
        List<String> symptoms = request.symptoms().stream()
                .map(String::trim)
                .filter(symptom -> !symptom.isEmpty())
                .distinct()
                .toList();
        if (symptoms.isEmpty()) {
            throw new ApiException(400, "Select at least one symptom.");
        }
        String duration = request.duration() == null || request.duration().isBlank()
                ? "Not provided" : request.duration().trim();
        String severity = request.severity() == null || request.severity().isBlank()
                ? "MILD" : request.severity().trim().toUpperCase(Locale.ROOT);
        String notes = request.notes() == null || request.notes().isBlank() ? null : request.notes().trim();
        HealthAnalyzer.Result assessment = analyzer.analyze(
                String.join(", ", symptoms) + " " + (notes == null ? "" : notes),
                duration, severity);

        SymptomRecordEntity record = new SymptomRecordEntity(
                userId, String.join(", ", symptoms), duration, severity, notes);
        record.setAnalysisResult(new AnalysisResultEntity(
                record, assessment.riskLevel(), assessment.guidance(), assessment.warning()));
        return toResponse(recordRepository.save(record), symptoms);
    }

    @Transactional(readOnly = true)
    public List<AnalysisResponse> history(long userId) {
        return recordRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(record -> toResponse(record, Arrays.stream(record.getSymptoms().split(", "))
                        .filter(symptom -> !symptom.isBlank()).toList()))
                .toList();
    }

    @Transactional
    public void clearHistory(long userId) {
        recordRepository.deleteAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    public long count(long userId) {
        return recordRepository.countByUserId(userId);
    }

    private AnalysisResponse toResponse(SymptomRecordEntity record, List<String> symptoms) {
        AnalysisResultEntity result = record.getAnalysisResult();
        return new AnalysisResponse(record.getId(), symptoms, record.getDuration(), record.getSeverity(),
                record.getNotes(), result.getRiskLevel(), result.getGuidance(), result.getWarning(),
                record.getCreatedAt());
    }
}
