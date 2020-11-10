package com.swordmaster.excalibur.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.dto.SignInAccountDTO;
import com.swordmaster.excalibur.dto.SignUpAccountDTO;
import com.swordmaster.excalibur.enumclass.UserRole;
import com.swordmaster.excalibur.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts/*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "(deprecated) 구글 로그인/회원가입", notes = "구글에서 리다이렉션을 받아 구글 로그인/회원가입을 진행합니다.")
    @GetMapping("/signin/google")
    public ResponseEntity<AccountDTO> googleSignin (@RequestParam(value="code") String authCode, @RequestParam(value="role") String role) throws JsonProcessingException {
        UserRole userRole = UserRole.valueOf(role.toUpperCase());

        return accountService.googleSignin(authCode, userRole);
    }

    @ApiOperation(value="회원가입", notes = "정보를 받아 회원가입을 진행하고 결과에 따른 응답메세지를 반환합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ResponseObject> signUp(@RequestBody SignUpAccountDTO signUpAccountDTO) {
        return accountService.signUp(signUpAccountDTO);
    }

    @ApiOperation(value="로그인", notes="로그인 성공 시 로그인 된 유저 정보와 jwt 토큰을 반환합니다.")
    @PostMapping("/signin")
    public ResponseEntity<ResponseObject> signIn(@RequestBody SignInAccountDTO signInAccountDTO) {

        return accountService.signIn(signInAccountDTO);
    }
}
