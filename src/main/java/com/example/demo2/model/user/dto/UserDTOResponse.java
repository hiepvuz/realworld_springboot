package com.example.demo2.model.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTOResponse {
    private String email;
    private String token;
    private String username;
    private String bio;
    private String image;
}
