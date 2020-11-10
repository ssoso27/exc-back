package com.swordmaster.excalibur.controller;

import com.swordmaster.excalibur.dto.QuizDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.service.QuizService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analysis-sessions/{analysisSessionId}/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @ApiOperation(value = "퀴즈 생성하기", notes = "강의자가 자신의 분석세션에 강의를 수동으로 추가합니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    @PostMapping("")
    public ResponseEntity<ResponseObject> create(@PathVariable Integer analysisSessionId, @RequestBody QuizDTO quizDTO) {
        if (!analysisSessionId.equals(quizDTO.getAnalysisSessionId())) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_MATCH_PATH_BODY, null), HttpStatus.BAD_REQUEST);
        }

        return quizService.create(quizDTO);
    }

    @ApiOperation(value = "퀴즈 목록보기", notes = "현재 분석세션에 추가된 퀴즈 목록을 확인합니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    @GetMapping("")
    public ResponseEntity<ResponseObject> list(@PathVariable Integer analysisSessionId) {
        return quizService.list(analysisSessionId);
    }

}
