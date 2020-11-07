package com.swordmaster.excalibur.util;

public class CourseCodeMaker {

    // 여섯자리 강의 초대 코드를 만듭니다.
    public String makeCode() {
        StringBuilder code = new StringBuilder(Integer.toString((int) (Math.random() * 1000000)));
        while (code.length() < 6) {
            code.insert(0, "0");
        }
        return code.toString();
    }
}
