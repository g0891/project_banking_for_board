package com.example.bank.dto;

public class TransactionDto {
    private long amount;
    private Long projectId;

    public TransactionDto() {
    }

    public TransactionDto(long amount, Long projectId) {
        this.amount = amount;
        this.projectId = projectId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
