package com.cinevision.auth_service.controller;

import com.cinevision.auth_service.dto.UserDTO;
import com.cinevision.auth_service.model.User;
import com.cinevision.auth_service.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_ShouldReturnUser() throws Exception {
        UserDTO dto = UserDTO.builder()
                .username("naveen")
                .email("naveen@gmail.com")
                .password("password123")
                .role("USER")
                .build();

        User user = User.builder()
                .id(1L)
                .username("naveen")
                .email("naveen@gmail.com")
                .password("encodedPassword")
                .role("USER")
                .build();

        // Mock service call
        Mockito.when(userService.registerUser(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())   // expect 200 OK
                .andExpect(jsonPath("$.username").value("naveen"))
                .andExpect(jsonPath("$.email").value("naveen@gmail.com"));
    }

    @Test
    void getUser_ShouldReturnUser_WhenExists() throws Exception {
        User user = User.builder()
                .id(1L)
                .username("naveen")
                .email("naveen@gmail.com")
                .password("encodedPassword")
                .role("USER")
                .build();

        Mockito.when(userService.findByUsername("naveen")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/auth/user/naveen"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("naveen"));
    }
}