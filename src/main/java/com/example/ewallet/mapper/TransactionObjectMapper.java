package com.example.ewallet.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.models.UserTransaction;

/**
 * The Class to map Database Objects and Data Transfer Objects or vice-versa
 */
public class TransactionObjectMapper {

    public static UserTransaction dtoToDO(UserTransactionDTO transaction) {
        UserTransaction userTransaction = new UserTransaction.TransactionBuilder()
                .setAmount(transaction.getAmount()).setId(transaction.getId()).setDetails(transaction.getDetails())
                .build();
        return userTransaction;
    }

    public static UserTransactionDTO doToDTO(UserTransaction transaction) {
        UserTransactionDTO userTransactionDTO = new UserTransactionDTO.TransactionDTOBuilder().setAmount(transaction.getAmount()).setId(transaction.getId())
                .setDetails(transaction.getDetails()).setTransactionDate(transaction.getTransactionDate())
                .build();
        return userTransactionDTO;
    }

    public static List<UserTransactionDTO> doToDTOList(List<UserTransaction> userTransactions) {
        return userTransactions.stream().map(TransactionObjectMapper::doToDTO).collect(Collectors.toList());
    }
}