package com.flownexa.auth.adapter.out.persistance;

import com.flownexa.auth.domain.model.Organization;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrganizationRepository extends JpaRepository<Organization, UUID> {
    Optional<Organization> findByName(String name);
    Optional<Organization> findByTaxId(String taxId);
}
