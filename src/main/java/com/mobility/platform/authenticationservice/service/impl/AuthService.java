package com.mobility.platform.authenticationservice.service.impl;

import com.mobility.platform.authenticationservice.dto.request.LoginRequest;
import com.mobility.platform.authenticationservice.dto.request.SignupRequest;
import com.mobility.platform.authenticationservice.dto.response.LoginResponse;
import com.mobility.platform.authenticationservice.entity.AppUser;

public interface AuthService {

    AppUser signup(SignupRequest signupRequest);

    LoginResponse login(LoginRequest loginRequest);

    AppUser findByEmail(String email);
}
