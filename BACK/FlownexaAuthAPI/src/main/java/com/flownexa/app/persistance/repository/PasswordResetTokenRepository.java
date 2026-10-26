package com.flownexa.app.persistance.repository;

import com.flownexa.app.entities.PasswordResetToken;
import com.flownexa.app.entities.Users;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {

    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    void deleteByUser(Users user);
}
