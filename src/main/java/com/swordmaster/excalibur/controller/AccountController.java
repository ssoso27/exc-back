package com.swordmaster.excalibur.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.enumclass.UserRole;
import com.swordmaster.excalibur.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts/*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/signin/google")
    public ResponseEntity<AccountDTO> googleSignin (@RequestParam(value="code") String authCode, @RequestParam(value="role") String role) throws JsonProcessingException {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());
        ResponseEntity<AccountDTO> result = accountService.googleSignin(authCode, userRole);

        return result;
    }
}
