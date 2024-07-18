package com.example.demostepinfor.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response<T,Y> {
    private String code;
    private String message;
    private T step;
    private Y condition;

    public Response(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
