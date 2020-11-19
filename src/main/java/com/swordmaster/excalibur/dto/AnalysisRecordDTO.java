package com.swordmaster.excalibur.dto;

import lombok.Setter;

import java.util.List;

@Setter
public class AnalysisRecordDTO {
    private CourseDTO course;
    private List<AnalysisSessionForRecordDTO> analysisSessions;
}
