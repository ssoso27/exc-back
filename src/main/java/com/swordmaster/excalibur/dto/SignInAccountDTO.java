package com.swordmaster.excalibur.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInAccountDTO {
    @ApiModelProperty(value = "이메일", example = "skykang104@swordmaster.com")
    private String email;

    @ApiModelProperty(value = "비밀번호", example = "sky1414")
    private String password;
}
