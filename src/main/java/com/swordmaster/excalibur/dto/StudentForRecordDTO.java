package com.swordmaster.excalibur.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class StudentForRecordDTO {
    @ApiModelProperty(value = "수강생 계정 pk")
    private Integer id;

    @ApiModelProperty(value = "이메일")
    private String email;

    @ApiModelProperty(value = "졸음도 정보 목록")
    private List<DrowsinessForRecordDTO> drowsinesses;

    @ApiModelProperty(value = "퀴즈 답안 목록")
    private List<SubmissionForRecordDTO> submissions;

}
