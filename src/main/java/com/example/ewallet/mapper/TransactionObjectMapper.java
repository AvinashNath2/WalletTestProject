package com.example.ewallet.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.example.ewallet.datatransferobject.UserTransactionDTO;
import com.example.ewallet.models.UserTransaction;

/**
 * The Class to map Database Objects and Data Transfer Objects or vice-versa
 */
public class TransactionObjectMapper {

    /** converts DTO to Database Object */
    public static UserTransaction dtoToDO(UserTransactionDTO transaction) {
        UserTransaction userTransaction = new UserTransaction.TransactionBuilder()
                .setAmount(transaction.getAmount()).setId(transaction.getId()).setDetails(transaction.getDetails())
                .build();
        return userTransaction;
    }

    /** converts Database Object to DTO*/
    public static UserTransactionDTO doToDTO(UserTransaction transaction) {
        UserTransactionDTO userTransactionDTO = new UserTransactionDTO.TransactionDTOBuilder().setAmount(transaction.getAmount()).setId(transaction.getId())
                .setDetails(transaction.getDetails()).setTransactionUpdatedDate(transaction.getTransactionUpdatedDate()).setTransactionHash(transaction.getTransactionHash()).setTransactionStatus(transaction.getTransactionStatus())
                .build();
        return userTransactionDTO;
    }

    /** converts Database Object to DTO List*/
    public static List<UserTransactionDTO> doToDTOList(List<UserTransaction> userTransactions) {
        return userTransactions.stream().map(TransactionObjectMapper::doToDTO).collect(Collectors.toList());
    }
}