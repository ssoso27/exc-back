package com.swordmaster.excalibur.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class AnalysisSessionForRecordDTO {
    @ApiModelProperty(value = "분석세션 pk", example = "1")
    private Integer id;

    @ApiModelProperty(value = "차시", example = "1")
    private Integer times;

    @ApiModelProperty(value = "세션 상태", example = "active")
    private String status;

    @ApiModelProperty(value = "세션의 수강생 목록")
    private List<StudentForRecordDTO> students;
}
