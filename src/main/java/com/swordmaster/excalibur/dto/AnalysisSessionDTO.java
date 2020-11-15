package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.entity.AnalysisSession;
import com.swordmaster.excalibur.entity.Course;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnalysisSessionDTO {
    @ApiModelProperty(value = "분석세션 pk", example = "1")
    private Integer id;

    @ApiModelProperty(value = "강의 pk", example = "28")
    private Integer courseId;

    @ApiModelProperty(value = "차시", example = "1")
    private Integer times;

}
