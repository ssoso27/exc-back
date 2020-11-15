package com.swordmaster.excalibur.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SessionStatus {
    ACTIVE("active"),
    CLOSE("close");

    final private String name;

    @Override
    public String toString() {
        return name;
    }
}
