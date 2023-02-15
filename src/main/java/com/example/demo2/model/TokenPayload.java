package com.example.demo2.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenPayload {
    private int userId;
    private String email;
}
