package com.example.demo2.exception.custom;


import com.example.demo2.model.CustomError;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class BaseExceptionHandler extends Exception{
    private Map<String, CustomError> error;

    public BaseExceptionHandler(CustomError customError) {
        this.error = new HashMap<>();
        this.error.put("error", customError);
    }
}
