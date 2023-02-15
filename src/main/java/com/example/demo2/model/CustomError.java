package com.example.demo2.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomError {
    private String code;
    private String message;
    private String traces;
}
