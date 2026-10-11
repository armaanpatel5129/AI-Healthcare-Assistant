package com.healthai.assistant.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "symptom_records")
public class SymptomRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String symptoms;
    @Column(length = 50)
    private String duration;
    @Column(length = 20)
    private String severity;
    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "symptomRecord", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private AnalysisResultEntity analysisResult;

    protected SymptomRecordEntity() {
    }

    public SymptomRecordEntity(Long userId, String symptoms, String duration, String severity, String notes) {
        this.userId = userId;
        this.symptoms = symptoms;
        this.duration = duration;
        this.severity = severity;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public String getDuration() {
        return duration;
    }

    public String getSeverity() {
        return severity;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public AnalysisResultEntity getAnalysisResult() {
        return analysisResult;
    }

    public void setAnalysisResult(AnalysisResultEntity analysisResult) {
        this.analysisResult = analysisResult;
    }
}
