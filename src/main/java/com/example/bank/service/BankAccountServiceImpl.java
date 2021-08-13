package com.example.bank.service;

import com.example.bank.dto.TransactionDto;
import com.example.bank.entity.BankAccountEntity;
import com.example.bank.entity.BankTransactionEntity;
import com.example.bank.mapper.TransactionMapper;
import com.example.bank.repository.BankAccountRepository;
import com.example.bank.repository.BankTransactionRepository;
import com.example.bank.rest.errorController.exception.BankIncorrectAmountException;
import com.example.bank.rest.errorController.exception.BankInsufficientFundsException;
import com.example.bank.rest.errorController.exception.BankProjectsDetailsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankAccountServiceImpl implements BankAccountService{

    Logger logger = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private final BankAccountRepository bankAccountRepository;
    private final BankTransactionRepository bankTransactionRepository;
    private final TransactionMapper transactionMapper;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, BankTransactionRepository bankTransactionRepository, TransactionMapper transactionMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.bankTransactionRepository = bankTransactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public BankAccountEntity createBankAccount(Long personId) {
        Optional<BankAccountEntity> bankAccountEntity = bankAccountRepository.findByPersonId(personId);
        return bankAccountEntity.orElse(bankAccountRepository.save(new BankAccountEntity(null, personId, 0L, null)));
    }

    @Override
    public Long getPersonBalance(Long personId) {
        return bankAccountRepository
                .findByPersonId(personId)
                .orElseGet(() -> createBankAccount(personId))
                .getBalance();
    }

    @Override
    public List<TransactionDto> getPersonTransactions(long personId) {
        BankAccountEntity bankAccountEntity = bankAccountRepository
                .findByPersonId(personId)
                .orElseGet(() -> createBankAccount(personId));

        return transactionMapper.bankTransactionEntityListToTransactionDtoList(bankAccountEntity.getBankTransactions());


    }

    @Override
    public void addMoney(Long personId, TransactionDto transactionDTO) {
        if (transactionDTO.getAmount() <= 0) {
            throw new BankIncorrectAmountException(String.format("It's not possible to add a non-positive amount = %d.", transactionDTO.getAmount()));
        }

        BankAccountEntity bankAccountEntity = bankAccountRepository
                .findByPersonId(personId)
                .orElseGet(() -> createBankAccount(personId));

        BankTransactionEntity bankTransactionEntity = new BankTransactionEntity(null,bankAccountEntity, transactionDTO.getAmount(), null);
        bankTransactionRepository.save(bankTransactionEntity);

        bankAccountEntity.setBalance(bankAccountEntity.getBalance() + transactionDTO.getAmount());
        bankAccountRepository.save(bankAccountEntity);
    }

    @Override
    public void payForProject(Long personId, TransactionDto transactionDTO) {
        if (transactionDTO.getAmount() <= 0) {
            throw new BankIncorrectAmountException(String.format("It's not possible to pay a non-positive amount = %d.", transactionDTO.getAmount()));
        }

        if (transactionDTO.getProjectId() == null) {
            throw new BankProjectsDetailsException("Project Id should be supplied, but nothing found");
        }

        BankAccountEntity bankAccountEntity = bankAccountRepository
                .findByPersonId(personId)
                .orElseGet(() -> createBankAccount(personId));

        if (bankAccountEntity.getBalance() < transactionDTO.getAmount()) {
            throw new BankInsufficientFundsException("There is not enough money to provide payment.");
        }

        BankTransactionEntity bankTransactionEntity = new BankTransactionEntity(null,bankAccountEntity, -transactionDTO.getAmount(), transactionDTO.getProjectId());
        bankTransactionRepository.save(bankTransactionEntity);

        bankAccountEntity.setBalance(bankAccountEntity.getBalance() - transactionDTO.getAmount());
        bankAccountRepository.save(bankAccountEntity);
    }

    @Override
    public long getProjectPaymentsAmount(long projectId) {
        return bankTransactionRepository.getProjectsAmount(projectId);
    }
}
