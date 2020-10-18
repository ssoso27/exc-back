package com.swordmaster.excalibur.dto;

import com.swordmaster.excalibur.enumclass.Message;
import lombok.Getter;

@Getter
public class ResponseMessage {
    private String message;

    public ResponseMessage(Message message) {
        this.message = message.getMessage();
    }
}
