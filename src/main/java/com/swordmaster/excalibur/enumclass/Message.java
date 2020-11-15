package com.swordmaster.excalibur.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {
    SIGNUP_SUCCESS("회원가입이 완료되었습니다.")
    , EXIST_ACCOUNT("이미 가입된 회원입니다.")
    , NON_EXIST_ACCOUNT("존재하지 않는 회원입니다.")
    , NOT_MATCH_EMAIL_PASSWORD("이메일과 비밀번호가 일치하지 않습니다.")
    , SIGNIN_SUCCESS("로그인이 완료되었습니다.")
    , CREATE_COURSE_SUCCESS("강의가 성공적으로 생성되었습니다.")
    , CREATE_QUIZ_SUCCESS("퀴즈가 성공적으로 생성되었습니다.")
    , DO_NOT_HAVE_THIS_SESSION("해당 분석세션을 생성한 강의자가 아닙니다.")
    , NOT_MATCH_PATH_BODY("path의 값과 body의 값이 일치하지 않습니다.")
    , LIST_QUIZ_SUCCESS("퀴즈 목록을 성공적으로 불러왔습니다.")
    , NOT_EXIST_ANALYSIS_SESSION("존재하지 않는 분석세션입니다.")
    , NOT_EXIST_QUIZ("존재하지 않는 퀴즈입니다.")
    , PICK_QUIZ_SUCCESS("퀴즈가 성공적으로 출제되었습니다.")
    , ALREADY_PICKED_QUIZ("이미 출제된 퀴즈입니다.")
    , INSERT_DROWSINESS_SUCCESS("졸음구간 저장에 성공하였습니다.")
    , LIST_COURSE_SUCCESS("강의 목록을 성공적으로 불러왔습니다.")
    , ALREADY_ALL_QUIZ_TRANSMIT("이미 출제된 모든 퀴즈를 보냈습니다.")
    , QUIZ_TRANSMIT_SUCCESS("최신 퀴즈를 성공적으로 불러왔습니다.")
    , GET_ACCOUNT_SUCCESS("사용자 정보를 성공적으로 가져왔습니다.")
    ;

    private final String message;

    @Override
    public String toString() {
        return message;
    }
}
