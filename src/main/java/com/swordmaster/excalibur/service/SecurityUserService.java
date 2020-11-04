package com.swordmaster.excalibur.service;

import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.repository.AccountRepository;
import com.swordmaster.excalibur.vo.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SecurityUserService implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }

        return new SecurityUser(maybeAccount.get());
    }
}
