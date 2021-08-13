package com.example.bank.mapper;

import com.example.bank.dto.TransactionDto;
import com.example.bank.entity.BankTransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

  /*  @Mappings({
            @Mapping(target = "personId", expression = "java(bankTransactionEntity.getBankAccount().getPersonId())")
    })*/
    TransactionDto bankTransactionEntityToTransactionDto(BankTransactionEntity bankTransactionEntity);

    List<TransactionDto> bankTransactionEntityListToTransactionDtoList(List<BankTransactionEntity> bankTransactionEntityList);

}
