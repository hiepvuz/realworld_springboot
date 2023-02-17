package com.example.demo2.service.impl;

import com.example.demo2.entity.User;
import com.example.demo2.exception.custom.CustomBadRequestException;
import com.example.demo2.model.user.dto.UserDTOLoginRequest;
import com.example.demo2.model.user.dto.UserDTOResponse;
import com.example.demo2.repository.UserRepository;
import com.example.demo2.util.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenUtil jwtTokenUti1;

    @Test
    void testAuthenticate_success() throws CustomBadRequestException {
        //given
        UserDTOLoginRequest userDTOLoginRequest = UserDTOLoginRequest.builder().email("email").password("password").build();
        Map<String, UserDTOLoginRequest> userLoginRequestMap = new HashMap<>();
        userLoginRequestMap.put("user", userDTOLoginRequest);
        Optional<User> userOptional = Optional
                .of(User.builder().id(1).email("email").password("password").username("username").build());
        Map<String, UserDTOResponse> expected = new HashMap<>();
        UserDTOResponse userDTOResponseExpected = UserDTOResponse.builder().email("email")
                .username("username").token("TOKEN").build();
        expected.put("user", userDTOResponseExpected);

        //when
        when(userRepository.findByEmail(userDTOLoginRequest.getEmail())).thenReturn(userOptional);
        when(passwordEncoder.matches("password", "password")).thenReturn(true);
        when(jwtTokenUti1.generateToken(userOptional.get(), 24L * 60 * 60)).thenReturn("TOKEN");
        Map<String, UserDTOResponse> actual = userService.authenticated(userLoginRequestMap);

        //then
        assertTrue(actual.containsKey("user"));
        UserDTOResponse userDTOResponseActual = actual.get("user");
        assertEquals(userDTOResponseExpected.getEmail(), userDTOResponseActual.getEmail());
        assertEquals(userDTOResponseExpected.getUsername(), userDTOResponseActual.getUsername());
        assertEquals(userDTOResponseExpected.getToken(), userDTOResponseActual.getToken());
    }

    @Test
    void testAuthenticate_throw_BadRequestException(){
        //given
        UserDTOLoginRequest userDTOLoginRequest = UserDTOLoginRequest.builder().email("unittest email")
                .password("unittest password").build();
        Map<String, UserDTOLoginRequest> userDTOLoginRequestMap = new HashMap<>();
        userDTOLoginRequestMap.put("user", userDTOLoginRequest);
        //when
        when(userRepository.findByEmail(userDTOLoginRequest.getEmail())).thenReturn(Optional.ofNullable(null));

        //verify
        assertThrows(CustomBadRequestException.class, () -> {
            userService.authenticated(userDTOLoginRequestMap);
        });
    }
}