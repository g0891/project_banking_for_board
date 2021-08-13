package com.example.bank.repository;

import com.example.bank.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccountEntity, Long> {
    Optional<BankAccountEntity> findByPersonId(long personId);
}
