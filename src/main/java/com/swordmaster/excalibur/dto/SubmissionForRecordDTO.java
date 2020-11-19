package com.swordmaster.excalibur.dto;

import io.swagger.annotations.ApiModelProperty;

public class SubmissionForRecordDTO {
    @ApiModelProperty(value = "제출 답안 pk")
    private Integer id;

    @ApiModelProperty(value = "퀴즈 pk")
    private Integer quizId;

    @ApiModelProperty(value = "문제")
    private String content;

    @ApiModelProperty(value = "보기1")
    private String example1;

    @ApiModelProperty(value = "보기2")
    private String example2;

    @ApiModelProperty(value = "보기3")
    private String example3;

    @ApiModelProperty(value = "정답 번호")
    private Integer answer;

    @ApiModelProperty(value = "제출 답안")
    private Integer submit;

    @ApiModelProperty(value = "정답 여부")
    private Integer isRight;

}
