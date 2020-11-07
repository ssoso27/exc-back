package com.swordmaster.excalibur.controller;

import com.swordmaster.excalibur.dto.CourseDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    CourseService courseService;

    @ApiOperation(value = "강의 생성하기", notes = "강의자가 강의를 생성합니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    @PostMapping("")
    public ResponseEntity<ResponseObject> create(@RequestBody CourseDTO courseDTO) {
        return courseService.create(courseDTO);
    }

}
