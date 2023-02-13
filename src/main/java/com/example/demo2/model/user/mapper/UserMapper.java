package com.example.demo2.model.user.mapper;

import java.util.Map;

import com.example.demo2.entity.User;
import com.example.demo2.model.user.dto.UserDTOCreate;
import com.example.demo2.model.user.dto.UserDTOResponse;

public class UserMapper {
    public static UserDTOResponse toUserDTOResponse(User user) {
        return UserDTOResponse.builder().email(user.getEmail()).token(user.getToken()).username(user.getUsername())
                .bio(user.getBio()).image(user.getImage()).build();
    }

    public static User toUser(UserDTOCreate userDTOCreate) {
        return User.builder().username(userDTOCreate.getUsername())
                .email(userDTOCreate.getEmail()).password(userDTOCreate.getPassword()).build();
    }

}
