package com.swordmaster.excalibur.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountType {
    NORMAL("normal"), GOOGLE("google");

    final private String name;

}
