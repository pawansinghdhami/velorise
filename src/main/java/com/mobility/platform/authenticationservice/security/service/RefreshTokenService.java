package com.mobility.platform.authenticationservice.security.service;

import com.mobility.platform.authenticationservice.entity.AppUser;
import com.mobility.platform.authenticationservice.entity.RefreshToken;

public interface RefreshTokenService {

    RefreshToken create(AppUser user);

    RefreshToken verify(String token);

    void revoke(String token);

    void revokeAll(AppUser user);
}
