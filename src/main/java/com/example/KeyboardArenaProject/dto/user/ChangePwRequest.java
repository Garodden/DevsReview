package com.example.KeyboardArenaProject.dto.user;

import lombok.Data;

@Data
public class ChangePwRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;
}