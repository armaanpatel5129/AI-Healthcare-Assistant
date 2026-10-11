package com.healthai.assistant.repository;

import com.healthai.assistant.model.SymptomRecordEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SymptomRecordRepository extends JpaRepository<SymptomRecordEntity, Long> {
    List<SymptomRecordEntity> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    long countByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
