package com.swordmaster.excalibur.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.enumclass.UserRole;
import com.swordmaster.excalibur.helper.GoogleAPIHelper;
import com.swordmaster.excalibur.repository.AccountRepository;
import com.swordmaster.excalibur.util.JwtTokenProvider;
import com.swordmaster.excalibur.vo.GoogleUserInfo;
import com.swordmaster.excalibur.vo.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    GoogleAPIHelper googleAPIHelper;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AccountRepository accountRepository;

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
                    .build()
        );

        Jwt jwt = jwtTokenProvider.generateToken(email);

        account.setAccessToken(jwt.getAccessToken());
        account.setRefreshToken(jwt.getRefreshToken());

        AccountDTO accountDTO = accountRepository.save(account).toDTO();

        responseEntity = new ResponseEntity<>(accountDTO, HttpStatus.OK);

        return responseEntity;
    }
}
