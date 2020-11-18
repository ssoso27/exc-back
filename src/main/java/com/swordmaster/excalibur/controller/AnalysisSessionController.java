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

    @ApiOperation(value = "세션 상태 확인하기", notes = "해당 세션에 대한 활성화 여부를 확인합니다. (active/close)")
    @GetMapping("/{analysisSessionId}/status")
    public ResponseEntity<ResponseObject> getStatus(@PathVariable Integer courseId, @PathVariable Integer analysisSessionId) {
        return analysisSessionService.getStatus(courseId, analysisSessionId);
    }

    @ApiOperation(value = "세션 종료하기", notes = "강의자가 분석 세션을 종료합니다.")
    @PostMapping("/{analysisSessionId}/close")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    public ResponseEntity<ResponseObject> close(@PathVariable Integer courseId, @PathVariable Integer analysisSessionId) {
        return analysisSessionService.close(courseId, analysisSessionId);
    }

}
