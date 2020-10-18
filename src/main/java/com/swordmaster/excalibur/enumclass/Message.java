package com.swordmaster.excalibur.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Message {
    SIGNUP_SUECCESS("회원가입이 완료되었습니다."),
    EXIST_ACCOUNT("이미 가입된 회원입니다.");

    private final String message;

    @Override
    public String toString() {
        return message;
    }
}
