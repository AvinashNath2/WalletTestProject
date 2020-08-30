package com.example.ewallet.datatransferobject;

import com.example.ewallet.WalletResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoreResponseDTO<T> {

    private String code;
    private String message;
    private T data;

    public static <T> ResponseEntity<CoreResponseDTO<T>> buildWithSuccess(WalletResponse response, T data) {
        // Constructing response with success status and details
        CoreResponseDTO<T> spResponseEntity = new CoreResponseDTO<>();
        spResponseEntity.setCode(response.getCode());
        spResponseEntity.setMessage(response.getMessage());
        spResponseEntity.setData(data);
        return ResponseEntity.status(response.getHttpStatus()).body(spResponseEntity);
    }

    public static ResponseEntity<CoreResponseDTO<Object>> buildWithFailure(
            WalletResponse response) {
        // Constructing response with errors provided and data is left as null
        CoreResponseDTO<Object> spResponseEntity = new CoreResponseDTO<>();
        spResponseEntity.setCode(response.getCode());
        spResponseEntity.setMessage(response.getMessage());
        return ResponseEntity.status(response.getHttpStatus()).body(spResponseEntity);
    }

    public static ResponseEntity<CoreResponseDTO<Object>> buildWithFailureMessage(
            WalletResponse response, String message) {
        // Constructing response with errors provided and data is left as null
        CoreResponseDTO<Object> spResponseEntity = new CoreResponseDTO<>();
        spResponseEntity.setCode(response.getCode());
        spResponseEntity.setMessage(message);
        return ResponseEntity.status(response.getHttpStatus()).body(spResponseEntity);
    }
}
