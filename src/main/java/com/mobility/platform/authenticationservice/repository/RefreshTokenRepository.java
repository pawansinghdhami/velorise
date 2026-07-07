package com.mobility.platform.authenticationservice.repository;

import com.mobility.platform.authenticationservice.entity.AppUser;
import com.mobility.platform.authenticationservice.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    // on scheduled clean up
    void deleteByUser(AppUser user);

    List<RefreshToken> findAllByUser(AppUser user);

    List<RefreshToken> findAllByUserAndRevokedFalse(AppUser user);
}
