package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.InsertDrowsinessDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.entity.Drowsiness;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.repository.DrowsinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DrowsinessService {

    @Autowired
    DrowsinessRepository drowsinessRepository;

    public ResponseEntity<ResponseObject> create(InsertDrowsinessDTO insertDTO) {
        Drowsiness drowsiness = drowsinessRepository.save(insertDTO.toDrowsiness());

        return new ResponseEntity<>(new ResponseObject(Message.INSERT_DROWSINESS_SUCCESS, drowsiness.toDTO()), HttpStatus.CREATED);
    }
}
