package com.swordmaster.excalibur.controller;

import com.swordmaster.excalibur.dto.QuizDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.service.QuizService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @ApiOperation(value = "퀴즈 생성하기", notes = "강의자가 자신의 분석세션에 강의를 수동으로 추가합니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    @PostMapping("")
    public ResponseEntity<ResponseObject> create(@RequestBody QuizDTO quizDTO) {
        return quizService.create(quizDTO);
    }

}
