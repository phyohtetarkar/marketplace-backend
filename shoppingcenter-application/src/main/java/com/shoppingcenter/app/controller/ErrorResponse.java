package com.shoppingcenter.app.controller;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String code;

    private String error;

    private List<String> errors;

}
