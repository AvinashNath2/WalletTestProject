package com.example.ewallet.datatransferobject;

import com.example.ewallet.WalletResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


/** Client Facing Response **/
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoreResponseDTO<T> {

    private String code;
    private String message;
    private T data;

    public static <T> ResponseEntity<CoreResponseDTO<T>> buildWithSuccess(WalletResponse response, T data) {
        // Constructing response with success status and details
        CoreResponseDTO<T> responseEntity = new CoreResponseDTO<>();
        responseEntity.setCode(response.getCode());
        responseEntity.setMessage(response.getMessage());
        responseEntity.setData(data);
        return ResponseEntity.status(response.getHttpStatus()).body(responseEntity);
    }

    public static ResponseEntity<CoreResponseDTO<Object>> buildWithFailure(
            WalletResponse response) {
        // Constructing response with errors provided and data is left as null
        CoreResponseDTO<Object> responseDTO = new CoreResponseDTO<>();
        responseDTO.setCode(response.getCode());
        responseDTO.setMessage(response.getMessage());
        return ResponseEntity.status(response.getHttpStatus()).body(responseDTO);
    }

    public static ResponseEntity<CoreResponseDTO<Object>> buildWithFailureCodes(
            String message, String code, HttpStatus httpStatus) {
        // Constructing response with errors provided by error message and codes
        CoreResponseDTO<Object> responseEntity = new CoreResponseDTO<>();
        responseEntity.setCode(code);
        responseEntity.setMessage(message);
        return ResponseEntity.status(httpStatus).body(responseEntity);
    }

}
