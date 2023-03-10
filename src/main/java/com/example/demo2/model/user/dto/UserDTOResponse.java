package com.example.demo2.model.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTOResponse {
    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;
}
