package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.entity.AnalysisSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisSessionRepository extends JpaRepository<AnalysisSession, Integer> {
    public List<AnalysisSession> findAllByCourseId(Integer courseId);
}
