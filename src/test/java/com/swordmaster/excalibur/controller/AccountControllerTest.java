package com.swordmaster.excalibur.controller;

import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.enumclass.UserRole;
import com.swordmaster.excalibur.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean private AccountService accountService;

    @Test
    public void googleSignupSuccess() throws Exception {
        // given
        AccountDTO accountDTO = AccountDTO.builder()
                .email("mockemail@mock.com")
                .name("mockname")
                .role(UserRole.STUDENT.getName())
                .accessToken("mocktoken")
                .build();
        final String authCode = "myauthcode";

        ResponseEntity<AccountDTO> responseEntity = new ResponseEntity<>(accountDTO, HttpStatus.OK);

        given(accountService.googleSignin(authCode, UserRole.STUDENT)).willReturn(responseEntity);

        // when
        ResultActions result = mvc
                .perform(get("/accounts/signin/google").param("code", authCode))
                .andDo(print());

        // then
        result.andExpect(status().isOk());
    }

}
