package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.enumclass.SignUpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Optional<Account> findById(Integer id);
    public Optional<Account> findByEmail(String email);
    public Optional<Account> findByEmailAndType(String email, SignUpType type);

    @Query(value = "SELECT a FROM Account a " +
            "WHERE a.id IN (SELECT uc.account.id FROM UserCourse uc WHERE uc.course.id = :courseId)")
    public List<Account> findAllStudentByCourseId(Integer courseId);
}
