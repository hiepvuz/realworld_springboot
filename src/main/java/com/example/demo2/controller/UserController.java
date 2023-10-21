package com.example.demo2.controller;

import com.example.demo2.entity.User;
import com.example.demo2.exception.custom.CustomBadRequestException;
import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.user.dto.UserDTOUpdate;
import org.springframework.web.bind.annotation.*;

import com.example.demo2.model.user.dto.UserDTOLoginRequest;
import com.example.demo2.model.user.dto.UserDTOResponse;
import com.example.demo2.model.user.dto.UserDTOCreate;

import com.example.demo2.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/users/login")
    public Map<String, UserDTOResponse> login(@RequestBody Map<String, UserDTOLoginRequest> userDTOLoginRequestMap)
            throws CustomBadRequestException {
        return userService.authenticated(userDTOLoginRequestMap);
    }

    @PostMapping(value = "/users")
    public Map<String, UserDTOResponse> registerUser(@RequestBody Map<String, UserDTOCreate> userDTOCreateMap) {
        return userService.create(userDTOCreateMap);
    }

    @GetMapping("/user")
    public Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException {
        return userService.getCurrentUser();
    }

    @PutMapping("/user")
    public Map<String, User> updateUser(@RequestBody Map<String, UserDTOUpdate> userDTOUpdateMap)
            throws CustomNotFoundException {
        return userService.update(userDTOUpdateMap);
    }
}
