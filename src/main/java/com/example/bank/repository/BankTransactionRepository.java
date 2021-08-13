package com.example.bank.repository;

import com.example.bank.entity.BankTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankTransactionRepository extends JpaRepository<BankTransactionEntity, Long> {
    //@Query(value = "SELECT -SUM(amount) FROM transaction WHERE project_id=:projectId", nativeQuery = true)
    @Query(value = "SELECT COALESCE((SELECT -SUM(amount) FROM transaction where project_id=:projectId),0) as SUM", nativeQuery = true)
    long getProjectsAmount(long projectId);
}
