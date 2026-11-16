package com.flownexa.auth.adapter.out.persistance;

import com.flownexa.auth.domain.enums.UserStatus;
import com.flownexa.auth.domain.model.Organization;
import com.flownexa.auth.domain.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {

    List<Users> findByOrganizationAndStatus(Organization organization, UserStatus status);
    
    Optional<Users> findByEmail(String email);
}
