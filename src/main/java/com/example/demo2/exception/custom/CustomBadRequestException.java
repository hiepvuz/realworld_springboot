package com.example.demo2.exception.custom;

import com.example.demo2.model.CustomError;

public class CustomBadRequestException extends BaseExceptionHandler {

    public CustomBadRequestException(CustomError customError) {
        super(customError);
    }
}
