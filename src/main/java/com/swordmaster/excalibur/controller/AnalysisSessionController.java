package com.swordmaster.excalibur.controller;

import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.service.AnalysisSessionService;
import com.swordmaster.excalibur.vo.SecurityUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses/{courseId}/analysis-sessions")
public class AnalysisSessionController {

    @Autowired
    AnalysisSessionService analysisSessionService;

    @ApiOperation(value = "분석세션 생성하기", notes = "강의자가 자신의 강의에 대한 분석세션을 생성합니다.")
    @PostMapping("")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    public ResponseEntity<ResponseObject> create(@PathVariable Integer courseId) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return analysisSessionService.create(courseId, securityUser.getId());
    }

}
