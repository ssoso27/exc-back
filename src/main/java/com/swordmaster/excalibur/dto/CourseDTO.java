package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.entity.Course;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CourseDTO {
    @ApiModelProperty(value = "강의 이름", example = "법과 사회")
    private String name;

    @ApiModelProperty(value = "개설한 강의자 pk id", example = "39")
    private Integer accountId;

    @ApiModelProperty(value = "초대 코드", example = "141414")
    private String code;

    public Course toCourse(Account account) {
        return Course.builder()
                .name(this.name)
                .account(account)
                .code(code)
                .build();
    }
}
