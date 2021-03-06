package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.InsertQuizDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.entity.AnalysisSession;
import com.swordmaster.excalibur.entity.Quiz;
import com.swordmaster.excalibur.entity.Submission;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.repository.AnalysisSessionRepository;
import com.swordmaster.excalibur.repository.QuizRepository;
import com.swordmaster.excalibur.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    AnalysisSessionRepository sessionRepository;

    public ResponseEntity<ResponseObject> create(InsertQuizDTO insertQuizDTO) {

        if (!sessionRepository.existsById(insertQuizDTO.getAnalysisSessionId())) {
            return new ResponseEntity<>(new ResponseObject(Message.DO_NOT_HAVE_THIS_SESSION, insertQuizDTO), HttpStatus.FORBIDDEN);
        }

        Quiz quiz = quizRepository.save(insertQuizDTO.toQuiz());
        return new ResponseEntity<>(new ResponseObject(Message.CREATE_QUIZ_SUCCESS, quiz.toDTO()), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseObject> autoCreate(InsertQuizDTO insertQuizDTO) {
        Quiz quiz = quizRepository.save(insertQuizDTO.toQuiz());
        return new ResponseEntity<>(new ResponseObject(Message.CREATE_QUIZ_SUCCESS, quiz.toDTO()), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseObject> list(Integer analysisSessionId) {
        Optional<AnalysisSession> maybeSession = sessionRepository.findById(analysisSessionId);

        if (maybeSession.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_EXIST_ANALYSIS_SESSION, null), HttpStatus.BAD_REQUEST);
        }

        List<Quiz> quizList = quizRepository.findAllByAnalysisSessionId(analysisSessionId);

        return new ResponseEntity<>(new ResponseObject(Message.LIST_QUIZ_SUCCESS, quizList.stream().map(Quiz::toDTO)), HttpStatus.OK);
    }

    public ResponseEntity<ResponseObject> pick(Integer analysisSessionId, Integer quizId) {
        Optional<Quiz> maybeQuiz = quizRepository.findByIdAndAnalysisSessionId(quizId, analysisSessionId);
        if (maybeQuiz.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_EXIST_QUIZ, null), HttpStatus.BAD_REQUEST);
        }

        Quiz quiz = maybeQuiz.get();
        if (quiz.getIsPick() == 1) {
            return new ResponseEntity<>(new ResponseObject(Message.ALREADY_PICKED_QUIZ, null), HttpStatus.BAD_REQUEST);
        }

        quiz.setIsPick(1);
        quizRepository.save(quiz);
        return new ResponseEntity<>(new ResponseObject(Message.PICK_QUIZ_SUCCESS, quiz.toDTO()), HttpStatus.OK);
    }

    public ResponseEntity<ResponseObject> getLatestQuiz(Integer accountId, Integer analysisSessionId) {
        List<Quiz> allQuizList = quizRepository.findAllByAnalysisSessionIdAndIsPick(analysisSessionId, 1);
        List<Submission> submissionList = submissionRepository.findAllByAccountIdAndQuizIdIn(accountId
                , allQuizList.stream().map(Quiz::getId).collect(Collectors.toList()));

        int allSize = allQuizList.size();
        int submitSize = submissionList.size();

        if (allSize > submitSize) { // 아직 수강생에게 보내지지 않은 퀴즈가 존재함
            List<Quiz> notTransmitList = allQuizList.subList(submitSize, allSize);
            List<Submission> insertSubmissionList = notTransmitList.stream()
                                                        .map(quiz -> new Submission(accountId, quiz.getId()))
                                                        .collect(Collectors.toList());
            submissionRepository.saveAll(insertSubmissionList);

            return new ResponseEntity<>(
                    new ResponseObject(Message.QUIZ_TRANSMIT_SUCCESS, allQuizList.get(allSize-1).toDTO())
                    , HttpStatus.OK);

        } else { // 출제된 모든 퀴즈를 수강생에게 보낸 상태
            return new ResponseEntity<>(new ResponseObject(Message.ALREADY_ALL_QUIZ_TRANSMIT, null), HttpStatus.OK);
        }
    }
}
