package com.example.demo2.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenPayload {
    private int userId;
    private String email;
}
