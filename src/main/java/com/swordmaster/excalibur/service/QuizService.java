package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.QuizDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.entity.Quiz;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.repository.AnalysisSessionRepository;
import com.swordmaster.excalibur.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    AnalysisSessionRepository sessionRepository;

    public ResponseEntity<ResponseObject> create(QuizDTO quizDTO) {

        if (!sessionRepository.existsById(quizDTO.getAnalysisSessionId())) {
            return new ResponseEntity<>(new ResponseObject(Message.DO_NOT_HAVE_THIS_SESSION, quizDTO), HttpStatus.FORBIDDEN);
        }

        Quiz quiz = quizRepository.save(quizDTO.toQuiz());
        return new ResponseEntity<>(new ResponseObject(Message.CREATE_QUIZ_SUCCESS, quiz.toDTO()), HttpStatus.CREATED);
    }
}
