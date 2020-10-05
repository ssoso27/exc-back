package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.dto.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    public ResponseEntity<AccountDTO> googleSignin(String authCode) {
        return null;
    }
}
