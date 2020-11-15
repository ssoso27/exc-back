package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.entity.AnalysisSession;
import com.swordmaster.excalibur.entity.Course;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.enumclass.SessionStatus;
import com.swordmaster.excalibur.repository.AnalysisSessionRepository;
import com.swordmaster.excalibur.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class AnalysisSessionService {

    @Autowired
    AnalysisSessionRepository sessionRepository;

    @Autowired
    CourseRepository courseRepository;

    public ResponseEntity<ResponseObject> create(Integer courseId, Integer accountId) {
        Optional<Course> maybeCourse = courseRepository.findById(courseId);
        if (maybeCourse.isEmpty()) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_EXIST_COURSE, null), HttpStatus.BAD_REQUEST);
        } else {
            if (!maybeCourse.get().getAccount().getId().equals(accountId)) {
                return new ResponseEntity<>(new ResponseObject(Message.DO_NOT_HAVE_THIS_COURSE, null), HttpStatus.FORBIDDEN);
            }
        }


        int times = 1;
        List<AnalysisSession> sessionList = sessionRepository.findAllByCourseId(courseId);
        if (sessionList.size() > 0) {
            times = sessionList.get(sessionList.size()-1).getTimes() + 1;
        }

        AnalysisSession analysisSession = AnalysisSession.builder()
                .course(Course.builder().id(courseId).build())
                .times(times)
                .status(SessionStatus.ACTIVE)
                .build();

        analysisSession = sessionRepository.save(analysisSession);

        return new ResponseEntity<>(
                new ResponseObject(Message.CREATE_ANALYSIS_SESSION_SUCCESS, analysisSession.toDTO())
                , HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseObject> getStatus(Integer courseId, Integer analysisSessionId) {
        Optional<AnalysisSession> maybeSession = sessionRepository.findById(analysisSessionId);

        if (maybeSession.isEmpty()) {
            return new ResponseEntity<>(
                    new ResponseObject(Message.NOT_EXIST_ANALYSIS_SESSION, null), HttpStatus.BAD_REQUEST);
        }

        AnalysisSession analysisSession = maybeSession.get();
        if (!courseId.equals(analysisSession.getCourse().getId())) {
            return new ResponseEntity<>(
                    new ResponseObject(Message.NOT_MATCH_COURSE_SESSION, null), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(
                new ResponseObject(Message.GET_ANALYSIS_SESSION_STATUS_SUCCESS, analysisSession.getStatus().getName())
                , HttpStatus.OK);
    }
}
