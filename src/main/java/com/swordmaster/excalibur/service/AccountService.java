package com.swordmaster.excalibur.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.dto.ResponseObject;
import com.swordmaster.excalibur.dto.SignUpAccountDTO;
import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.enumclass.SignUpType;
import com.swordmaster.excalibur.enumclass.Message;
import com.swordmaster.excalibur.enumclass.UserRole;
import com.swordmaster.excalibur.helper.GoogleAPIHelper;
import com.swordmaster.excalibur.repository.AccountRepository;
import com.swordmaster.excalibur.util.JwtTokenProvider;
import com.swordmaster.excalibur.vo.GoogleUserInfo;
import com.swordmaster.excalibur.vo.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    GoogleAPIHelper googleAPIHelper;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AccountRepository accountRepository;

    private Boolean validate(SignUpAccountDTO signUpAccountDTO, String encodedPassword) {
        return passwordEncoder.matches(signUpAccountDTO.getPassword(), encodedPassword);
    }

    public ResponseEntity<AccountDTO> googleSignin(String authCode, UserRole userRole) throws JsonProcessingException {
        ResponseEntity<AccountDTO> responseEntity;

        String jwtToken = googleAPIHelper.getJWTToken(authCode, userRole);
        GoogleUserInfo googleUserInfo = googleAPIHelper.decodeJWT(jwtToken);

        String email = googleUserInfo.getEmail();

        Account account = accountRepository.findByEmail(email).orElseGet(() ->
                    Account.builder()
                    .email(googleUserInfo.getEmail())
                    .name(googleUserInfo.getName())
                    .picture(googleUserInfo.getPicture())
                    .role(userRole)
                    .type(SignUpType.GOOGLE)
                    .build()
        );

        Jwt jwt = jwtTokenProvider.generateToken(email);

        account.setAccessToken(jwt.getAccessToken());
        account.setRefreshToken(jwt.getRefreshToken());

        AccountDTO accountDTO = accountRepository.save(account).toDTO();

        responseEntity = new ResponseEntity<>(accountDTO, HttpStatus.OK);

        return responseEntity;
    }

    public ResponseEntity<ResponseObject> signUp(SignUpAccountDTO signUpAccountDTO) {
        if (accountRepository.findByEmailAndType(signUpAccountDTO.getEmail(), SignUpType.NORMAL).isPresent()) {
            return new ResponseEntity<>(new ResponseObject(Message.EXIST_ACCOUNT, signUpAccountDTO.getEmail()), HttpStatus.BAD_REQUEST);
        }

        accountRepository.save(signUpAccountDTO.toAccount(passwordEncoder.encode(signUpAccountDTO.getPassword())));
        return new ResponseEntity<>(new ResponseObject(Message.SIGNUP_SUCCESS, signUpAccountDTO.getEmail()), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseObject> signIn(SignUpAccountDTO signUpAccountDTO) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(signUpAccountDTO.getEmail());

        if (maybeAccount.isEmpty()) {
           return new ResponseEntity<>(new ResponseObject(Message.NON_EXIST_ACCOUNT, signUpAccountDTO.getEmail()), HttpStatus.BAD_REQUEST);
        }

        Account account = maybeAccount.get();
        if (!validate(signUpAccountDTO, account.getPassword())) {
            return new ResponseEntity<>(new ResponseObject(Message.NOT_MATCH_EMAIL_PASSWORD, signUpAccountDTO.getEmail()), HttpStatus.BAD_REQUEST);
        }

        Jwt jwt = jwtTokenProvider.generateToken(account.getEmail());

        account.setAccessToken(jwt.getAccessToken());
        account.setRefreshToken(jwt.getRefreshToken());
        accountRepository.save(account);

        AccountDTO accountDTO = account.toDTO();

        return new ResponseEntity<>(new ResponseObject(Message.SIGNIN_SUCCESS, accountDTO), HttpStatus.OK);
    }
}
