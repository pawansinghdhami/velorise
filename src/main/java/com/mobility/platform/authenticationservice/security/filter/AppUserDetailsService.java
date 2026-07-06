package com.mobility.platform.authenticationservice.security.filter;

import com.mobility.platform.authenticationservice.entity.AppUser;
import com.mobility.platform.authenticationservice.repository.UserRepository;
import com.mobility.platform.authenticationservice.service.impl.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        // Implementation for loading user by email
        AppUser appUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new AppUserDetails(appUser);
    }
}
