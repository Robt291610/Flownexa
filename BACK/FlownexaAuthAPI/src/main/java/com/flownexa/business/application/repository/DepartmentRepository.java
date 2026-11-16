package com.flownexa.business.application.repository;

import com.flownexa.business.domain.model.Department;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    List<Department> findByOrganizationIdOrderByNameAsc(UUID organizationId);

    Optional<Department> findByIdAndOrganizationId(UUID id, UUID organizationId);

    Optional<Department> findByOrganizationIdAndNameIgnoreCase(UUID organizationId, String name);
}