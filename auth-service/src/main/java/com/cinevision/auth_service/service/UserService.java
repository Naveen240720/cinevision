package com.cinevision.auth_service.service;

import com.cinevision.auth_service.dto.UserDTO;
import com.cinevision.auth_service.model.User;
import com.cinevision.auth_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor  // ✅ generates constructor for final fields
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole())
                .email(userDTO.getEmail())
                .build();

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
