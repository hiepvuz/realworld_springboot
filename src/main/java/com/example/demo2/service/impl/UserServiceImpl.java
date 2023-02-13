package com.example.demo2.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.example.demo2.exception.custom.CustomBadRequestException;
import com.example.demo2.exception.custom.CustomNotFoundException;
import com.example.demo2.model.CustomError;
import com.example.demo2.model.profile.dto.ProfileDTOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo2.entity.User;
import com.example.demo2.model.user.dto.UserDTOCreate;
import com.example.demo2.model.user.dto.UserDTOLoginRequest;
import com.example.demo2.model.user.dto.UserDTOResponse;
import com.example.demo2.model.user.mapper.UserMapper;
import com.example.demo2.repository.UserRepository;
import com.example.demo2.service.UserService;
import com.example.demo2.util.JwtTokenUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Map<String, UserDTOResponse> authenticated(Map<String, UserDTOLoginRequest> userDTOLoginRequestMap) throws CustomBadRequestException {
        UserDTOLoginRequest userDTOLoginRequest = userDTOLoginRequestMap.get("user");

        Optional<User> optionalUser = userRepository.findByEmail(userDTOLoginRequest.getEmail());
        boolean isAuthen = false;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userDTOLoginRequest.getPassword(), user.getPassword())) {
                isAuthen = true;
            }
        }
        if (!isAuthen) {
            throw new CustomBadRequestException(CustomError.builder()
                    .code("400").message("Username or password incorrect").build());
        }

        return buildUserDTOResponse(optionalUser.get());
    }

    @Override
    public Map<String, UserDTOResponse> create(Map<String, UserDTOCreate> userDTOCreateMap) {
        User user = UserMapper.toUser(userDTOCreateMap.get("user"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return buildUserDTOResponse(user);
    }

    public User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            return userRepository.findByEmail(email).get();
        }
        return null;
    }

    @Override
    public Map<String, UserDTOResponse> getCurrentUser() throws CustomNotFoundException {
        User user = getLoggedInUser();
        if (user != null) {
            return buildUserDTOResponse(user);
        } else {
            throw new CustomNotFoundException(CustomError.builder().code("404").message("User not exist").build());
        }
    }

    @Override
    public Map<String, ProfileDTOResponse> getProfile(String userName) throws CustomNotFoundException {
        User userLoggedIn = getLoggedInUser();
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        if (!optionalUser.isPresent()) {
            throw new CustomNotFoundException(CustomError.builder().code("404").message("User not found").build());
        }
        User user = optionalUser.get();
        boolean isFollowing = false;
        for (User u : user.getFollowers()) {
            if (u.getId() == (userLoggedIn.getId())) {
                isFollowing = true;
                break;
            }
        }
        return buildProfileDTOResponse(user, isFollowing);
    }

    @Override
    public Map<String, ProfileDTOResponse> follow(String username) throws CustomNotFoundException {
        User userLoggedIn = getLoggedInUser();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new CustomNotFoundException(CustomError.builder().code("404").message("User not found").build());
        }
        User user = optionalUser.get();
        user.getFollowers().add(userLoggedIn);
        user = userRepository.save(user);
        return buildProfileDTOResponse(user, true);
    }

    @Override
    public Map<String, ProfileDTOResponse> unfollow(String username) throws CustomNotFoundException {
        User userLoggedIn = getLoggedInUser();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new CustomNotFoundException(CustomError.builder().code("404").message("User not found").build());
        }
        User user = optionalUser.get();
        user.getFollowers().remove(userLoggedIn);
        user = userRepository.save(user);
        return buildProfileDTOResponse(user, false);
    }


    public Map<String, UserDTOResponse> buildUserDTOResponse(User user) {
        Map<String, UserDTOResponse> wrapper = new HashMap<>();
        UserDTOResponse userDTOResponse = UserMapper.toUserDTOResponse(user);
        userDTOResponse.setToken(jwtTokenUtil.generateToken(user, 10000L));
        wrapper.put("user", userDTOResponse);
        return wrapper;
    }

    private Map<String, ProfileDTOResponse> buildProfileDTOResponse(User user, boolean isFollowing) {
        Map<String, ProfileDTOResponse> wrapper = new HashMap<>();
        ProfileDTOResponse profileDTOResponse = ProfileDTOResponse.builder().username(user.getUsername())
                .bio(user.getBio()).image(user.getImage()).isFollowing(isFollowing).build();
        wrapper.put("profile", profileDTOResponse);
        return wrapper;
    }

}
