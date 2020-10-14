package com.swordmaster.excalibur.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {
    STUDENT("student"),
    TEACHER("teacher")
    ;

    final private String name;
}
