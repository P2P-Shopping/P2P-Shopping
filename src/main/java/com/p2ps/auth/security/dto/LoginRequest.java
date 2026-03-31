package com.p2ps.auth.security.dto;

import lombok.*;
@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;


}