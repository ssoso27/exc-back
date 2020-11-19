package com.swordmaster.excalibur.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnalysisRecordDTO {
    @ApiModelProperty(value = "강의 정보")
    private CourseDTO course;

    @ApiModelProperty(value = "분석 세션 목록")
    private List<AnalysisSessionForRecordDTO> analysisSessions;
}
