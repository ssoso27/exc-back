package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.dto.AccountDTO;
import com.swordmaster.excalibur.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByEmail(String email);
//
//    public AccountDTO signUp(String email, String name, String picture, String token) {
//    }
//
//    public AccountDTO signIn(String email, String token) {
//    }
}
