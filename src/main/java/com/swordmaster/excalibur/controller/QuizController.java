package com.swordmaster.excalibur.controller;

import com.swordmaster.excalibur.dto.InsertQuizDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.service.QuizService;
import com.swordmaster.excalibur.vo.SecurityUser;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analysis-sessions/{analysisSessionId}/quizzes")
public class QuizController {
    @Autowired
    QuizService quizService;

    @ApiOperation(value = "퀴즈 생성하기", notes = "강의자가 자신의 분석세션에 강의를 수동으로 추가합니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    @PostMapping("")
    public ResponseEntity<ResponseObject> create(@PathVariable Integer analysisSessionId, @RequestBody InsertQuizDTO insertQuizDTO) {
        if (!analysisSessionId.equals(insertQuizDTO.getAnalysisSessionId())) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_MATCH_PATH_BODY, null), HttpStatus.BAD_REQUEST);
        }

        return quizService.create(insertQuizDTO);
    }

    @ApiOperation(value = "(AI) 자동생성된 퀴즈 추가하기", notes = "AI가 자동생성한 퀴즈를 추가합니다.")
    @PostMapping("/auto")
    public ResponseEntity<ResponseObject> autoCreate(@PathVariable Integer analysisSessionId, @RequestBody InsertQuizDTO insertQuizDTO) {
        if (!analysisSessionId.equals(insertQuizDTO.getAnalysisSessionId())) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_MATCH_PATH_BODY, null), HttpStatus.BAD_REQUEST);
        }

        return quizService.autoCreate(insertQuizDTO);
    }

    @ApiOperation(value = "퀴즈 목록보기", notes = "현재 분석세션에 추가된 퀴즈 목록을 확인합니다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    @GetMapping("")
    public ResponseEntity<ResponseObject> list(@PathVariable Integer analysisSessionId) {
        return quizService.list(analysisSessionId);
    }

    @ApiOperation(value = "퀴즈 출제하기", notes = "특정 퀴즈를 선택하여 출제한다.")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_TEACHER')")
    @PostMapping("/{quizId}/pick")
    public ResponseEntity<ResponseObject> pick(@PathVariable Integer analysisSessionId, @PathVariable Integer quizId) {
        return quizService.pick(analysisSessionId, quizId);
    }

    @ApiOperation(value = "(수강생) 최신 출제된 퀴즈 가져오기", notes = "가장 최신의 출제된 퀴즈를 아직 받지 못했으면 가져온다 (받았으면 null)")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_STUDENT')")
    @GetMapping("/not-transmit")
    public ResponseEntity<ResponseObject> getLatestQuiz(@PathVariable Integer analysisSessionId) {
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return quizService.getLatestQuiz(securityUser.getId(), analysisSessionId);
    }

}
