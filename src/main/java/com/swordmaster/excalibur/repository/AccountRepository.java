package com.swordmaster.excalibur.repository;

import com.swordmaster.excalibur.entity.Account;
import com.swordmaster.excalibur.enumclass.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByEmail(String email);
    public Optional<Account> findByEmailAndType(String email, AccountType type);
}
