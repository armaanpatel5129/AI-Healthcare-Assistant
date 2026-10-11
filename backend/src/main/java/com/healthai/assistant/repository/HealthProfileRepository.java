package com.healthai.assistant.repository;

import com.healthai.assistant.model.HealthProfileEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthProfileRepository extends JpaRepository<HealthProfileEntity, Long> {
    Optional<HealthProfileEntity> findByUserId(Long userId);
}
