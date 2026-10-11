package com.healthai.assistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "analysis_results")
public class AnalysisResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "symptom_record_id", nullable = false, unique = true)
    private SymptomRecordEntity symptomRecord;
    @Column(name = "risk_level", nullable = false, length = 30)
    private String riskLevel;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String guidance;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String warning;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected AnalysisResultEntity() {
    }

    public AnalysisResultEntity(SymptomRecordEntity symptomRecord, String riskLevel, String guidance, String warning) {
        this.symptomRecord = symptomRecord;
        this.riskLevel = riskLevel;
        this.guidance = guidance;
        this.warning = warning;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public String getGuidance() {
        return guidance;
    }

    public String getWarning() {
        return warning;
    }
}
