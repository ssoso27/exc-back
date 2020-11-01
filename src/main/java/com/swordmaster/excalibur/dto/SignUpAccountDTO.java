package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.enumclass.SignUpType;
import com.swordmaster.excalibur.enumclass.UserRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpAccountDTO {
    @ApiModelProperty(value = "pk", example = "35")
    private Integer id;

    @ApiModelProperty(value = "이름", example = "강하늘")
    private String name;

    @ApiModelProperty(value = "이메일", example = "skykang104@swordmaster.com")
    private String email;

    @ApiModelProperty(value = "비밀번호", example = "sky1414")
    private String password;

    @ApiModelProperty(value = "Role(student, teacher)", example = "student")
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
