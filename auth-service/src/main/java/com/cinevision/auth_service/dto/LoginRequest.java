package com.cinevision.auth_service.dto;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class LoginRequest {
    private String username;
    private String password;
}
