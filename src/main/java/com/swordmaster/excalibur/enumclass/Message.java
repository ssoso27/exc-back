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
    ;

    private final String message;

    @Override
    public String toString() {
        return message;
    }
}
