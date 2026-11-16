package com.mobility.platform.authenticationservice.controller.auth;

import com.mobility.platform.authenticationservice.dto.request.LoginRequest;
import com.mobility.platform.authenticationservice.dto.request.SignupRequest;
import com.mobility.platform.authenticationservice.dto.response.LoginResponse;
import com.mobility.platform.authenticationservice.entity.AppUser;
import com.mobility.platform.authenticationservice.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService  authService;

    @PostMapping("/signup")
    public AppUser signup(@Valid @RequestBody SignupRequest request) {
       return authService.signup(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return  authService.login(request);
    }
}

