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

    PARCREATED201("PARCREATED201", "Parking has bean created.",HttpStatus.CREATED),
    ACCOUNT200("ACCOUNT200", "Account created successfully.",HttpStatus.OK),
    NOTVALID406("NOTVALID406", "Not a valid Input.",HttpStatus.NOT_ACCEPTABLE),
    DELETED200("DELETED200", "Parking removed successfully.",HttpStatus.OK);

    private String code;
    private String message;
    private HttpStatus httpStatus;

}
