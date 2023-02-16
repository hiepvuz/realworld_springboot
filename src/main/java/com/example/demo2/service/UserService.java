package com.example.demo2.service;

import java.util.Map;

import com.example.demo2.entity.User;
import com.example.demo2.exception.custom.CustomBadRequestException;
import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.profile.dto.ProfileDTOResponse;
import com.example.demo2.model.user.dto.UserDTOCreate;
import com.example.demo2.model.user.dto.UserDTOLoginRequest;
import com.example.demo2.model.user.dto.UserDTOResponse;
import com.example.demo2.model.user.dto.UserDTOUpdate;

public interface UserService {

    Map<String, UserDTOResponse> authenticated(Map<String, UserDTOLoginRequest> userDTOLoginRequestMap) throws CustomBadRequestException;

    Map<String, UserDTOResponse> create(Map<String, UserDTOCreate> userDTOCreateMap);

    Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException;

    Map<String, ProfileDTOResponse> getProfile(String userName) throws CustomNotFoundException;

    Map<String, ProfileDTOResponse> follow(String username) throws CustomNotFoundException;

    Map<String, ProfileDTOResponse> unfollow(String username) throws CustomNotFoundException;

    User getLoggedInUser();

    Map<String, User> update(Map<String, UserDTOUpdate> userDTOUpdateMap) throws CustomNotFoundException;
}
