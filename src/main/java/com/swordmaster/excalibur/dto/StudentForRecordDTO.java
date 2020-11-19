package com.swordmaster.excalibur.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class StudentForRecordDTO {
    @ApiModelProperty(value = "수강생 계정 pk")
    private Integer id;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "졸음도 정보 목록")
    private List<DrowsinessForRecordDTO> drowsinesses;

    @ApiModelProperty(value = "퀴즈 답안 목록")
    private List<SubmissionForRecordDTO> submitQuizzes;

}
