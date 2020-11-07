package com.swordmaster.excalibur.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    STUDENT("student", "ROLE_STUDENT"),
    TEACHER("teacher", "ROLE_TEACHER")
    ;

    final private String name;
    final private String authority;

    @Override
    public String toString() {
        return name;
    }
}
