package com.swordmaster.excalibur.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.dto.ResponseMessage;
import com.swordmaster.excalibur.dto.SignUpAccountDTO;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.enumclass.UserRole;
import com.swordmaster.excalibur.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/signin/google")
    public ResponseEntity<AccountDTO> googleSignin (@RequestParam(value="code") String authCode, @RequestParam(value="role") String role) throws JsonProcessingException {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());

        return accountService.googleSignin(authCode, userRole);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signUp(@RequestBody SignUpAccountDTO signUpAccountDTO) {
        return accountService.signUp(signUpAccountDTO);
    }
}
