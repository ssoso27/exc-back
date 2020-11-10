package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.InsertQuizDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.entity.AnalysisSession;
import com.swordmaster.excalibur.entity.Quiz;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.repository.AnalysisSessionRepository;
import com.swordmaster.excalibur.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    AnalysisSessionRepository sessionRepository;

    public ResponseEntity<ResponseObject> create(InsertQuizDTO insertQuizDTO) {

        if (!sessionRepository.existsById(insertQuizDTO.getAnalysisSessionId())) {
            return new ResponseEntity<>(new ResponseObject(Message.DO_NOT_HAVE_THIS_SESSION, insertQuizDTO), HttpStatus.FORBIDDEN);
        }

        Quiz quiz = quizRepository.save(insertQuizDTO.toQuiz());
        return new ResponseEntity<>(new ResponseObject(Message.CREATE_QUIZ_SUCCESS, quiz.toInsertDTO()), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseObject> list(Integer analysisSessionId) {
        Optional<AnalysisSession> maybeSession = sessionRepository.findById(analysisSessionId);

        if (maybeSession.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_EXIST_ANALYSIS_SESSION, null), HttpStatus.BAD_REQUEST);
        }

        List<Quiz> quizList = quizRepository.findAllByAnalysisSessionId(analysisSessionId);

        return new ResponseEntity<>(new ResponseObject(Message.LIST_QUIZ_SUCCESS, quizList.stream().map(Quiz::toInsertDTO)), HttpStatus.OK);
    }
}
