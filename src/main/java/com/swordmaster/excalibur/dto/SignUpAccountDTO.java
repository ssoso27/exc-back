package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.enumclass.SignUpType;
import com.swordmaster.excalibur.enumclass.UserRole;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpAccountDTO {
    private String name;
    private String email;
    private String password;
    private String role;

    public Account toAccount(String encodedPassword) {
        return Account.builder()
                .name(name)
                .email(email)
                .password(encodedPassword)
                .role(UserRole.valueOf(role.toUpperCase()))
                .type(SignUpType.NORMAL)
                .build();
    }
}
