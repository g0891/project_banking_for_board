package com.example.bank.rest.controller;

import com.example.bank.dto.TransactionDto;
import com.example.bank.service.BankAccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.path}/bankAccount")
public class BankAccountController {

    private final Logger logger = LoggerFactory.getLogger(BankAccountController.class);

    private final BankAccountServiceImpl bankAccountService;

    public BankAccountController(BankAccountServiceImpl bankAccountService) {
         this.bankAccountService = bankAccountService;
    }

    @GetMapping("/{personId}/balance")
    public ResponseEntity<Long> getPersonBalance(@PathVariable long personId) {
        logger.info("Requested balance for person id = {}", personId);
        Long balance = bankAccountService.getPersonBalance(personId);
        logger.info("Provided balance for person id = {}", personId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/{personId}/history")
    public ResponseEntity<List<TransactionDto>> getTransactionsForPerson(@PathVariable long personId) {
        logger.info("Requested transactions for person id = {}", personId);
        List<TransactionDto> transactionDtoList = bankAccountService.getPersonTransactions(personId);
        logger.info("Provided transactions list for person id = {}", personId);
        return ResponseEntity.ok().body(transactionDtoList);
    }

    @PutMapping("/{personId}/addMoney")
    public ResponseEntity addMoney(@PathVariable long personId, @RequestBody TransactionDto transactionDto) {
        logger.info("Requested to add money to account for person id = {}", personId);
        bankAccountService.addMoney(personId, transactionDto);
        logger.info("Added money to account for person id = {}", personId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{personId}/pay")
    public ResponseEntity payForProject(@PathVariable long personId, @RequestBody TransactionDto transactionDto) {
        logger.info("Requested to pay money for project {} from account for person id = {}", transactionDto.getProjectId(), personId);
        bankAccountService.payForProject(personId, transactionDto);
        logger.info("Payment provided for project {} from account for person id = {}", transactionDto.getProjectId(), personId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/projectPayments/{projectId}")
    public ResponseEntity<Long> getTotalPaymentsForProject(@PathVariable long projectId) {
        logger.info("Requested total amount payed for project id {}.", projectId);
        long amount = bankAccountService.getProjectPaymentsAmount(projectId);
        logger.info("Total payment amount provided for project id {}", projectId);
        return ResponseEntity.ok().body(amount);
    }



}
