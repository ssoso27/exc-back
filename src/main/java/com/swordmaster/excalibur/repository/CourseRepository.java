package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    public List<Course> findAllByAccountId(Integer accountId);
    public Optional<Course> findByCode(String code);

    @Query(value = "SELECT c FROM Course c " +
            "WHERE c.id IN (SELECT uc.course.id FROM UserCourse uc WHERE uc.account.id = :accountId)")
    public List<Course> findAllByStudentId(Integer accountId);
}
