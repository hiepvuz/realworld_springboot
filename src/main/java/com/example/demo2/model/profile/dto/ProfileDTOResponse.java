package com.example.demo2.model.profile.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDTOResponse {
    private String username;
    private String bio;
    private String image;
    private boolean isFollowing;
}
