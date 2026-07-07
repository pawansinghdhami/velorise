package com.mobility.platform.authenticationservice.dto.response;

import lombok.Data;

@Data
public class LoginResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;
}
