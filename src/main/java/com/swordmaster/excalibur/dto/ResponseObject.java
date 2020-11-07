package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.enumclass.Message;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseObject {
    @ApiModelProperty(value = "응답 메세지", example = "존재하지 않는 회원입니다.")
    private String message;

    @ApiModelProperty(value = "응답 데이터")
    private Object data;

    public ResponseObject(Message message, Object data) {
        this.message = message.toString();
        this.data = data;
    }
}
