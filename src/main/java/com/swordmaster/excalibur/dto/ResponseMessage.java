package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.enumclass.Message;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ResponseMessage {
    @ApiModelProperty(value = "응답 메세지", example = "존재하지 않는 회원입니다.")
    private String message;

    public ResponseMessage(Message message) {
        this.message = message.getMessage();
    }
}
