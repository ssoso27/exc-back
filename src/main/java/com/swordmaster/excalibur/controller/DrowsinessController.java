package com.swordmaster.excalibur.controller;

import com.swordmaster.excalibur.dto.InsertDrowsinessDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.service.DrowsinessService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analysis-sessions/{analysisSessionId}/accounts/{accountId}/drowsinesses")
public class DrowsinessController {

    @Autowired
    DrowsinessService drowsinessService;

    @ApiOperation(value = "수강생 졸음구간 추가하기", notes = "수강생이 졸았다 판단되는 구간을 저장합니다.")
    @PostMapping("")
    public ResponseEntity<ResponseObject> create(
            @PathVariable Integer analysisSessionId
            , @PathVariable Integer accountId
            , @RequestBody InsertDrowsinessDTO insertDTO) {

        if (!(analysisSessionId.equals(insertDTO.getAnalysisSessionId()) && accountId.equals(insertDTO.getAccountId()))) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_MATCH_PATH_BODY, null), HttpStatus.BAD_REQUEST);
        }

        return drowsinessService.create(insertDTO);
    }
}
