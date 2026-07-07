package com.mobility.platform.authenticationservice.security.service;

import com.mobility.platform.authenticationservice.entity.AppUser;
import com.mobility.platform.authenticationservice.entity.RefreshToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    @Value("${security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Override
    public RefreshToken create(AppUser user) {
        return null;
    }

    @Override
    public RefreshToken verify(String token) {
        return null;
    }

    @Override
    public void revoke(String token) {

    }

    @Override
    public void revokeAll(AppUser user) {

    }
}
