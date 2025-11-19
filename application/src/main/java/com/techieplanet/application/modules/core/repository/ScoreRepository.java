package com.techieplanet.application.modules.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techieplanet.application.modules.core.model.Score;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByStudentId(Long studentId);
    Optional<Score> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
}