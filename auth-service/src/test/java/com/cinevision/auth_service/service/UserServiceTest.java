package com.cinevision.auth_service.service;

import com.cinevision.auth_service.dto.UserDTO;
import com.cinevision.auth_service.model.User;
import com.cinevision.auth_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldSaveUser() {
        UserDTO dto = UserDTO.builder()
                .username("naveen")
                .email("naveen@gmail.com")
                .password("password123")
                .role("USER")
                .build();

        User savedUser = User.builder()
                .id(1L)
                .username("naveen")
                .email("naveen@gmail.com")
                .password("encodedPassword")
                .role("USER")
                .build();

        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPassword()).isEqualTo("encodedPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findByUsername_ShouldReturnUser() {
        User user = User.builder()
                .id(1L)
                .username("naveen")
                .email("naveen@gmail.com")
                .password("encodedPassword")
                .role("USER")
                .build();

        when(userRepository.findByUsername("naveen")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("naveen");

        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("naveen");
    }
}
