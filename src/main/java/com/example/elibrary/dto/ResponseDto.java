package com.example.elibrary.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> extends RepresentationModel<ResponseDto<T>> {
    private int code;
    private String message;
    private boolean success;
    private T data;
    private List<ErrorDto> errors;

}
