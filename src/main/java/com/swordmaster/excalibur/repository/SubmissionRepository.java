package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.dto.SubmissionForRecordDTO;
import com.swordmaster.excalibur.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    public List<Submission> findAllByAccountId(Integer accountId);
    public Optional<Submission> findByAccountIdAndQuizId(Integer accountId, Integer quizId);
    public List<Submission> findAllByAccountIdAndQuizIdIn(Integer accountId, List<Integer> quizIds);

}
