package com.cinevision.auth_service.dto;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UserDTO {
    private String username;
    private String role;
    private String password;
    private String email;
}
