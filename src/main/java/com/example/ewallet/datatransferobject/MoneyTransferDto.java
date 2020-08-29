package com.example.ewallet.datatransferobject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoneyTransferDto {

    private UserDTO sender;
    private UserDTO receiver;
    private UserTransactionDTO transactionInfo;

}
