package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.CourseDTO;
import com.swordmaster.excalibur.entity.Course;
import com.swordmaster.excalibur.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public ResponseEntity<CourseDTO> create(CourseDTO courseDTO) {
        courseRepository.save(courseDTO.toCourse());

        return new ResponseEntity<>(courseDTO, HttpStatus.CREATED);
    }
}
