package com.swordmaster.excalibur.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DrowsinessForRecordDTO {
    @ApiModelProperty(value = "졸음도 pk")
    private Integer id;

    @ApiModelProperty(value = "졸음 상태")
    private String status;

    @ApiModelProperty(value = "구간 시작 (초)")
    private Integer startSecond;

    @ApiModelProperty(value = "구간 종료 (초)")
    private Integer endSecond;
}
