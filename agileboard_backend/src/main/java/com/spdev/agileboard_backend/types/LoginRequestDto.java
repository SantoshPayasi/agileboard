package com.spdev.agileboard_backend.types;

import lombok.Data;


@Data
public class LoginRequestDto {
   
    private String email;
    private String password;
}