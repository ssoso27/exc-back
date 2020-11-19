package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.CourseDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.entity.AnalysisSession;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.repository.AccountRepository;
import com.swordmaster.excalibur.repository.AnalysisSessionRepository;
import com.swordmaster.excalibur.repository.CourseRepository;
import com.swordmaster.excalibur.util.CourseCodeMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AnalysisSessionRepository sessionRepository;

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
}
