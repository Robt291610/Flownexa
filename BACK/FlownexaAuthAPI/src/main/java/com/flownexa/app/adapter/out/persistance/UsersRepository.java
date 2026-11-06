package com.flownexa.app.adapter.out.persistance;

import com.flownexa.app.domain.enums.UserStatus;
import com.flownexa.app.domain.model.Organization;
import com.flownexa.app.domain.model.Users;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {

    List<Users> findByOrganizationAndStatus(Organization organization, UserStatus status);
    
    Optional<Users> findByEmail(String email);
}
