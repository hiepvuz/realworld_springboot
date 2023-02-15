package com.example.demo2.model.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTOCreate {
    private String username;
    private String email;
    private String password;

}
