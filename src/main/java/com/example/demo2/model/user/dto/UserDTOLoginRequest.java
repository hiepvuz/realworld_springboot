package com.example.demo2.model.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTOLoginRequest {
    private String email;
    private String password;
}
