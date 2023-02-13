package com.example.demo2.controller;


import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.profile.dto.ProfileDTOResponse;
import com.example.demo2.model.user.dto.UserDTOResponse;
import com.example.demo2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;

    @GetMapping("{username}")
    public Map<String, ProfileDTOResponse> getProfile(@PathVariable("username") String username) throws CustomNotFoundException {
        return userService.getProfile(username);
    }

    @PostMapping("/{username}/follow")
    public Map<String, ProfileDTOResponse> follow(@PathVariable("username") String username) throws CustomNotFoundException {
        return userService.follow(username);
    }

    @DeleteMapping("/{username}/unfollow")
    public Map<String, ProfileDTOResponse> unfollow(@PathVariable("username") String username) throws CustomNotFoundException {
        return userService.unfollow(username);
    }
}
