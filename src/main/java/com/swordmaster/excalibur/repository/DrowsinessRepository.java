package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.entity.Drowsiness;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrowsinessRepository extends JpaRepository<Drowsiness, Integer> {
    public List<Drowsiness> findAllByAccountId(Integer accountId);
}
