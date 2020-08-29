package com.example.ewallet.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ErrorResponseDTO {
    private String field;
    private String message;
    private Object rejectedValue;

    public static ErrorResponseDTO createError(String message) {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO();
        responseDTO.setMessage(message);
        return responseDTO;
    }
}