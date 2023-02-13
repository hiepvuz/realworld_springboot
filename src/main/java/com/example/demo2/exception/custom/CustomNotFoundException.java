package com.example.demo2.exception.custom;

import com.example.demo2.model.CustomError;

public class CustomNotFoundException extends BaseExceptionHandler {

    public CustomNotFoundException(CustomError customError) {
        super(customError);
    }
}
