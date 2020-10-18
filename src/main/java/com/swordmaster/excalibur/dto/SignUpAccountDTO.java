package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.enumclass.AccountType;
import com.swordmaster.excalibur.enumclass.UserRole;
import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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
                .type(AccountType.NORMAL)
                .build();
    }
}
