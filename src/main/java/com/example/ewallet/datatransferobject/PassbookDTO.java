package com.example.ewallet.datatransferobject;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/** Client Facing Model of PassBook **/
@Getter
@Setter
@Builder
public class PassbookDTO {

    private UserDTO userProfile;
    private List<UserTransactionDTO> transactions;

}
