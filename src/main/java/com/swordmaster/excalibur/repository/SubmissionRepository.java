package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {
    public List<Submission> findAllByAccountId(Integer accountId);
}
