package com.shoppingcenter.app.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String code;

    private String error;

    public ErrorResponse() {
    }

    public ErrorResponse(String code, String error) {
        this.code = code;
        this.error = error;
    }

}
