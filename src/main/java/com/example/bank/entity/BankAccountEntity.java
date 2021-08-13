package com.example.bank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "account")
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "person_id")
    private long personId;

    private long balance;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankAccount")
    List<BankTransactionEntity> bankTransactions;

    public BankAccountEntity() {
    }

    public BankAccountEntity(Long id, long personId, long balance, List<BankTransactionEntity> bankTransactions) {
        this.id = id;
        this.personId = personId;
        this.balance = balance;
        this.bankTransactions = bankTransactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public List<BankTransactionEntity> getBankTransactions() {
        return bankTransactions;
    }

    public void setBankTransactions(List<BankTransactionEntity> bankTransactions) {
        this.bankTransactions = bankTransactions;
    }
}
