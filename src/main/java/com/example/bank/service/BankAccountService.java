package com.example.bank.service;

import com.example.bank.dto.TransactionDto;
import com.example.bank.entity.BankAccountEntity;

import java.util.List;

public interface BankAccountService {
    BankAccountEntity createBankAccount(Long personId);
    Long getPersonBalance(Long personId);

    List<TransactionDto> getPersonTransactions(long personId);

    void addMoney(Long personId, TransactionDto transactionDTO);

    void payForProject(Long personId, TransactionDto transactionDTO);

    long getProjectPaymentsAmount(long projectId);
}
