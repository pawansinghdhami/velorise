package com.mobility.platform.authenticationservice.security.service;

import com.mobility.platform.authenticationservice.exception.DuplicateEmailException;
import com.mobility.platform.authenticationservice.exception.UserNotFoundException;
import com.mobility.platform.authenticationservice.security.config.JwtProperties;
import com.mobility.platform.authenticationservice.dto.request.LoginRequest;
import com.mobility.platform.authenticationservice.dto.request.SignupRequest;
import com.mobility.platform.authenticationservice.dto.response.LoginResponse;
import com.mobility.platform.authenticationservice.entity.AppUser;
import com.mobility.platform.authenticationservice.repository.AppUserRepository;
import com.mobility.platform.authenticationservice.security.userdetails.AppUserDetails;
import com.mobility.platform.util.Role;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;

    @Override
    public AppUser signup(SignupRequest signupRequest) {
        if(appUserRepository.findByEmail(signupRequest.getEmail()).isPresent()){
            throw new DuplicateEmailException("Email already registered");
        }
        AppUser appUser = new AppUser();
        appUser.setEmail(signupRequest.getEmail());
        appUser.setFirstName(signupRequest.getFirstName());
        appUser.setLastName(signupRequest.getLastName());
        appUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        appUser.setRole(Role.USER);
        appUser.setStatus("ACTIVE");
        appUser.setPhone(signupRequest.getPhone());
        appUser.setCreatedBy("SYSTEM");
        appUser.setUpdatedBy("SYSTEM");
        return appUserRepository.save(appUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getEmail(),
                                loginRequest.getPassword()
                        )
                );
        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(appUserDetails);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setTokenType("Bearer");
        loginResponse.setExpiresIn(jwtProperties.getExpiration());
        return loginResponse;
    }

    @Override
    public AppUser findByEmail(String email) {
       return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
