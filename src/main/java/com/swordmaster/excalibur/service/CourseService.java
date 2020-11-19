package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.*;
import com.swordmaster.excalibur.entity.*;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.enumclass.SessionStatus;
import com.swordmaster.excalibur.repository.*;
import com.swordmaster.excalibur.util.CourseCodeMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AnalysisSessionRepository sessionRepository;

    @Autowired
    DrowsinessRepository drowsinessRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    SubmissionRepository submissionRepository;

    public ResponseEntity<ResponseObject> create(CourseDTO courseDTO) {
        Optional<Account> maybeAccount = accountRepository.findById(courseDTO.getAccountId());

        if (maybeAccount.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject(Message.NON_EXIST_ACCOUNT, courseDTO.getAccountId()), HttpStatus.BAD_REQUEST);
        }

        // 초대 코드 만들어주기
        CourseCodeMaker courseCodeMaker = new CourseCodeMaker();
        courseDTO.setCode(courseCodeMaker.makeCode());

        courseRepository.save(courseDTO.toCourse(maybeAccount.get()));

        return new ResponseEntity<>(new ResponseObject(Message.CREATE_COURSE_SUCCESS, courseDTO), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseObject> listActiveCourse(Integer accountId) {
        List<AnalysisSession> analysisSessions = sessionRepository.findAllActiveByAccountId(accountId);

        return new ResponseEntity<>(
                new ResponseObject(Message.LIST_ANALYSIS_SESSION_SUCCESS
                        , analysisSessions.stream().map(AnalysisSession::toDTO))
                , HttpStatus.OK);
    }

    public ResponseEntity<ResponseObject> listAllAnalysis(Integer accountId, Integer courseId) {
        Optional<Course> maybeCourse = courseRepository.findById(courseId);

        if (maybeCourse.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_EXIST_COURSE, null), HttpStatus.BAD_REQUEST);
        }

        Course course = maybeCourse.get();
        if (!accountId.equals(course.getAccount().getId())) {
            return new ResponseEntity<>(new ResponseObject(Message.DO_NOT_HAVE_THIS_COURSE, null), HttpStatus.BAD_REQUEST);
        }

        AnalysisRecordDTO recordDTO = new AnalysisRecordDTO();
        recordDTO.setCourse(course.toDTO());

        // 이 강의의 수강생 목록 받아오기
        List<Account> students = accountRepository.findAllStudentByCourseId(courseId);

        // 1. 세션 정보들 받아오기기 (close)
        List<AnalysisSessionForRecordDTO> analysisSessionForRecordDTOs = new ArrayList<>();
        List<AnalysisSession> analysisSessions = sessionRepository.findAllByCourseIdAndStatus(courseId, SessionStatus.CLOSE);
        for (AnalysisSession analysisSession: analysisSessions) {
            List<StudentForRecordDTO> studentDTOs = new ArrayList<>();
            AnalysisSessionForRecordDTO analysisSessionForRecordDTO = AnalysisSessionForRecordDTO
                    .builder()
                    .id(analysisSession.getId())
                    .times(analysisSession.getTimes())
                    .status(analysisSession.getStatus().getName())
                    .build();

            // 2. 수강생의 졸음도 정보들 받아오기
            for (Account student: students) {
                StudentForRecordDTO studentDTO = StudentForRecordDTO.builder()
                        .id(student.getId())
                        .email(student.getEmail())
                        .build();

                List<Drowsiness> drowsinesses = drowsinessRepository.findAllByAccountId(student.getId());
                studentDTO.setDrowsinesses(drowsinesses.stream().map(Drowsiness::toForRecordDTO).collect(Collectors.toList()));

                // 3. 수강생의 퀴즈 및 제출 답안 받아오기 (isPick)
                List<SubmissionForRecordDTO> submissionForRecordDTOs = new ArrayList<>();
                // 3-1. 퀴즈 받아오기
                List<Quiz> quizzes = quizRepository.findAllByAnalysisSessionIdAndIsPick(analysisSession.getId(), 1);

                // 3-2. 답안 받아오기
                for (Quiz quiz: quizzes) {
                    Optional<Submission> maybeSubmission = submissionRepository.findByAccountIdAndQuizId(student.getId(), quiz.getId());
                    Submission submission = maybeSubmission.orElse(
                            Submission.builder()
                                .id(-1)
                                .account(student)
                                .quiz(quiz)
                                .submit(-1)
                                .isRight(0)
                                .build()
                    );

                    // 3-3. DTO로 변환
                    SubmissionForRecordDTO submissionForRecordDTO = new SubmissionForRecordDTO(quiz, submission);
                    submissionForRecordDTOs.add(submissionForRecordDTO);
                }

                studentDTO.setSubmitQuizzes(submissionForRecordDTOs);
                studentDTOs.add(studentDTO);
            }

            analysisSessionForRecordDTO.setStudents(studentDTOs);
            analysisSessionForRecordDTOs.add(analysisSessionForRecordDTO);
        }

        recordDTO.setAnalysisSessions(analysisSessionForRecordDTOs);

        return new ResponseEntity<>(new ResponseObject(Message.LIST_ANAYSIS_SUCCESS, recordDTO), HttpStatus.OK);
    }
}
