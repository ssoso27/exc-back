package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.QuizDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.entity.Quiz;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    public ResponseEntity<ResponseObject> create(QuizDTO quizDTO) {
        // TODO: 이 분석세션이 이 강의자의 것이 맞는지 확인 필요

        Quiz quiz = quizRepository.save(quizDTO.toQuiz());

        return new ResponseEntity<>(new ResponseObject(Message.CREATE_QUIZ_SUCCESS, quizDTO), HttpStatus.CREATED);
    }
}
