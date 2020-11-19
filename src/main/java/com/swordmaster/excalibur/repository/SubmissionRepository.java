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

//    @Query(value = "SELECT s.id, q.id as quizId, q.content, q.example1, q.example2, q.example3, q.answer, s.submit, s.isRight " +
//            "FROM Submission s " +
//            "JOIN Quiz q " +
//            "ON s.quiz.id = q.id " +
//            "WHERE s.account.id = :accountId AND q.isPick = 1")
//    public List<SubmissionForRecordDTO> findAllRecordDTOByAccountId(Integer accountId);
}
