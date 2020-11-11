package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.entity.AnalysisSession;
import com.swordmaster.excalibur.entity.Quiz;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class QuizDTO {
    @ApiModelProperty(value = "퀴즈 pk", example = "4")
    private Integer id;

    @ApiModelProperty(value = "분셕세션 pk", example = "1")
    private Integer analysisSessionId;

    @ApiModelProperty(value = "퀴즈 내용", example = "호랑이를 영어로 하면?")
    private String content;

    @ApiModelProperty(value = "보기1", example = "tigar")
    private String example1;

    @ApiModelProperty(value = "보기2", example = "taiger")
    private String example2;

    @ApiModelProperty(value = "보기3", example = "tiger")
    private String example3;

    @ApiModelProperty(value = "정답 번호", example = "3")
    private Integer answer;

    @ApiModelProperty(value = "출제 여부 (0 false, 1 true)", example = "0")
    private Integer isPick;

    public Quiz toQuiz() {
        return Quiz.builder()
                .analysisSession(AnalysisSession.builder().id(this.analysisSessionId).build())
                .content(this.content)
                .example1(this.example1)
                .example2(this.example2)
                .example3(this.example3)
                .answer(this.answer)
                .isPick(this.isPick)
                .build();
    }
}
