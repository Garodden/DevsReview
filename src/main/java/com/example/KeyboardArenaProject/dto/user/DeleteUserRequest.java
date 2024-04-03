package com.example.KeyboardArenaProject.dto.user;

import lombok.Data;

@Data
public class DeleteUserRequest {
    private String password;
    private String confirmPassword;
}