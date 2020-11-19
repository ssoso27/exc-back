package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.entity.Quiz;
import com.swordmaster.excalibur.entity.Submission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class SubmissionForRecordDTO {
    public SubmissionForRecordDTO(Quiz quiz, Submission submission) {
        this.id = submission.getId();
        this.quizId = quiz.getId();
        this.content = quiz.getContent();
        this.example1 = quiz.getExample1();
        this.example2 = quiz.getExample2();
        this.example3 = quiz.getExample3();
        this.answer = quiz.getAnswer();
        this.submit = submission.getSubmit();
        this.isRight = submission.getIsRight();
    }

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
