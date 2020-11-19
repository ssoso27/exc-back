package com.swordmaster.excalibur.controller;

import com.swordmaster.excalibur.dto.CourseDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.service.CourseService;
import com.swordmaster.excalibur.vo.SecurityUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "현재 active 상태인 강의 목록 확인", notes = "현재 active 상태인 강의 목록과 그 분석세션을 가져옵니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_STUDENT')")
    @GetMapping("/active")
    public ResponseEntity<ResponseObject> listActiveCourse() {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return courseService.listActiveCourse(securityUser.getId());
    }

}
