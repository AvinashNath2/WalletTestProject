package com.example.ewallet.datatransferobject;

import lombok.Getter;
import lombok.Setter;

/** Client Facing Model of MoneyTransfer **/
@Getter
@Setter
public class MoneyTransferDto {

    private UserDTO sender;
    private UserDTO receiver;
    private UserTransactionDTO transactionInfo;

}
