package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.entity.AnalysisSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisSessionRepository extends JpaRepository<AnalysisSession, Integer> {
    public List<AnalysisSession> findAllByCourseId(Integer courseId);

    @Query(value = "SELECT s FROM AnalysisSession s WHERE s.status='active' AND s.course.id in (" +
            "SELECT uc.course.id FROM UserCourse uc WHERE uc.account.id=:accountId)")
    public List<AnalysisSession> findAllActiveByAccountId(@Param("accountId") Integer accountId);
}
