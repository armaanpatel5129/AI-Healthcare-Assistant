package com.healthai.assistant.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "health_profiles")
public class HealthProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    private Integer age;
    @Column(length = 20)
    private String gender;
    @Column(name = "blood_group", length = 10)
    private String bloodGroup;
    @Column(name = "height_cm", precision = 5, scale = 2)
    private BigDecimal heightCm;
    @Column(name = "weight_kg", precision = 5, scale = 2)
    private BigDecimal weightKg;
    @Column(columnDefinition = "TEXT")
    private String allergies;
    @Column(name = "existing_conditions", columnDefinition = "TEXT")
    private String existingConditions;
    @Column(name = "emergency_contact", length = 100)
    private String emergencyContact;

    protected HealthProfileEntity() {
    }

    public HealthProfileEntity(Long userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public BigDecimal getHeightCm() {
        return heightCm;
    }

    public void setHeightCm(BigDecimal heightCm) {
        this.heightCm = heightCm;
    }

    public BigDecimal getWeightKg() {
        return weightKg;
    }

    public void setWeightKg(BigDecimal weightKg) {
        this.weightKg = weightKg;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getExistingConditions() {
        return existingConditions;
    }

    public void setExistingConditions(String existingConditions) {
        this.existingConditions = existingConditions;
    }
}
