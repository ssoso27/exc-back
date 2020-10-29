package com.swordmaster.excalibur.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {
    @ApiModelProperty(value = "이메일", example = "skykang104@swordmaster.com")
    private String email;

    @ApiModelProperty(value = "이름", example = "강하늘")
    private String name;

    @ApiModelProperty(value = "Role (student, teacher)", example = "student")
    private String role;

    @ApiModelProperty(value = "계정 가입 유형 (normal, google)", example = "normal")
    private String type;

    @ApiModelProperty(value = "accessToken")
    private String accessToken;

    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;
}
