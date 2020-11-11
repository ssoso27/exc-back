package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    public List<Quiz> findAllByAnalysisSessionId(Integer analysisSessionId);
    public List<Quiz> findAllByAnalysisSessionIdAndIsPick(Integer analysisSessionId, Integer isPick);
    public Optional<Quiz> findByIdAndAnalysisSessionId(Integer id, Integer analysisSessionId);

}
