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
    private Integer createrId;

    public Course toCourse() {
        return Course.builder()
                .name(this.name)
                .account(Account.builder().id(this.createrId).build())
                .build();
    }
}
