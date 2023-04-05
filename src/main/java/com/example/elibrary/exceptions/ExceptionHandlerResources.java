package com.example.elibrary.exceptions;
import com.example.elibrary.dto.ErrorDto;
import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.service.validator.AppStatusCode;
import com.example.elibrary.service.validator.AppStatusMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.stream.Collectors.toList;
@RestControllerAdvice
public class ExceptionHandlerResources {
    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest()
                .body(ResponseDto.<Void>builder()
                        .code(AppStatusCode.OK_CODE)
                        .message(AppStatusMessages.VALIDATION_ERROR)
                        .errors(e.getBindingResult().getFieldErrors().stream()
                                .map(f-> new ErrorDto(f.getField(),f.getDefaultMessage()))
                                .collect(toList()))
                        .build());
    }
}
