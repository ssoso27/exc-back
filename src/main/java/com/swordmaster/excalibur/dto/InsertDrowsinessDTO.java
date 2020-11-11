package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.entity.AnalysisSession;
import com.swordmaster.excalibur.entity.Drowsiness;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InsertDrowsinessDTO {
    @ApiModelProperty(value = "수강생 pk", example = "11")
    private Integer accountId;

    @ApiModelProperty(value = "분석세션 pk", example = "1")
    private Integer analysisSessionId;

    @ApiModelProperty(value = "분석결과 상태", example = "drowsiness")
    private String status;

    @ApiModelProperty(value = "졸음 판정 시작 시간(정수)", example = "10")
    private Integer startSecond;

    @ApiModelProperty(value = "졸음 판정 끝 시간(정수)", example = "20")
    private Integer endSecond;

    public Drowsiness toDrowsiness() {
        return Drowsiness.builder()
                .account(Account.builder().id(accountId).build())
                .analysisSession(AnalysisSession.builder().id(analysisSessionId).build())
                .status(status)
                .startSecond(startSecond)
                .endSecond(endSecond)
                .build();
    }
}
