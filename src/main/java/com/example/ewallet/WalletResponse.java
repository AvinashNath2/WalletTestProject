package com.example.ewallet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum WalletResponse {

    SUCCESS200("SUCCESS200", "Success",HttpStatus.OK),
    USERCREATED201("USERCREATED201", "User created successfully",HttpStatus.CREATED),
    FAILURE400("FAILURE400", "Unable to created User",HttpStatus.BAD_REQUEST),
    USERNOTFOUND404("USERNOTFOUND404", "User not found",HttpStatus.NOT_FOUND),
    TRANSACTIONCREATED201("TRANSACTIONCREATED201", "Transaction created successfully",HttpStatus.CREATED),
    TRANSACTIONUPDATED200("TRANSACTIONUPDATED200", "Transaction updated successfully",HttpStatus.OK);

    private String code;
    private String message;
    private HttpStatus httpStatus;

}
