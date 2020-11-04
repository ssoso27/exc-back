package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.enumclass.UserRole;
import com.swordmaster.excalibur.repository.AccountRepository;
import com.swordmaster.excalibur.vo.SecurityUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityUserServiceTest {
    @InjectMocks
    SecurityUserService securityUserService;

    @Mock
    AccountRepository accountRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loadUserByUsername() {
        String email = "kangsky@swordmaster.com";
        Account account = Account.builder()
                .email("kangsky@swordmaster.com")
                .password("asdf1234")
                .name("강하늘")
                .role(UserRole.STUDENT)
                .accessToken("mocktoken")
                .build();
        SecurityUser securityUser = new SecurityUser(account);

        // given
        when(accountRepository.findByEmail(email)).thenReturn(Optional.of(account));

        // when
        SecurityUser result = (SecurityUser) securityUserService.loadUserByUsername(email);

        // then
        assertEquals(securityUser.getAuthorities(), result.getAuthorities());
        assertEquals(securityUser.getUsername(), result.getUsername());
        assertEquals(securityUser.getPassword(), result.getPassword());
    }
}