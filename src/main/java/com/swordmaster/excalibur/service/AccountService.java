package com.swordmaster.excalibur.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.helper.GoogleAPIHelper;
import com.swordmaster.excalibur.repository.AccountRepository;
import com.swordmaster.excalibur.vo.GoogleUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    GoogleAPIHelper googleAPIHelper;

    @Autowired
    AccountRepository accountRepository;

    public ResponseEntity<AccountDTO> googleSignin(String authCode) throws JsonProcessingException {
        ResponseEntity<AccountDTO> responseEntity;

        String jwtToken = googleAPIHelper.getJWTToken(authCode);
        GoogleUserInfo googleUserInfo = googleAPIHelper.decodeJWT(jwtToken);

        String email = googleUserInfo.getEmail();
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        System.out.println(maybeAccount);

        if (maybeAccount.isEmpty()) {
            // 회원가입
            Account account = Account.builder()
                    .email(googleUserInfo.getEmail())
                    .name(googleUserInfo.getName())
                    .picture(googleUserInfo.getPicture())
                    .accessToken(googleUserInfo.getToken())
                    .role("student")
                    .build();
            System.out.println(account);
            AccountDTO accountDTO = accountRepository.save(account).toDTO();
            System.out.println(accountDTO);
            responseEntity = new ResponseEntity<>(accountDTO, HttpStatus.OK);
        } else {
            // 로그인
//            AccountDTO accountDTO = accountRepository.signIn(googleUserInfo.getEmail(), googleUserInfo.getToken());
//            responseEntity = new ResponseEntity<>(accountDTO, HttpStatus.OK);
        }

        AccountDTO accountDTO = AccountDTO.builder()
                .name(googleUserInfo.getName())
                .email(googleUserInfo.getEmail())
                .accessToken(googleUserInfo.getToken())
                .role("asdf")
                .build();
        responseEntity = new ResponseEntity<>(accountDTO, HttpStatus.OK);

        return responseEntity;
    }
}
