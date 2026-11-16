package com.flownexa.business.service;

import com.flownexa.auth.adapter.out.persistance.OrganizationRepository;
import com.flownexa.auth.config.AuthenticatedUser;
import com.flownexa.auth.domain.model.Organization;
import com.flownexa.business.application.dto.DepartmentRequest;
import com.flownexa.business.application.dto.DepartmentResponse;
import com.flownexa.business.application.repository.DepartmentRepository;
import com.flownexa.business.domain.model.Department;
import com.flownexa.business.exception.DepartmentAlreadyExistsException;
import com.flownexa.business.exception.DepartmentNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;

import java.util.UUID;
import org.springframework.stereotype.Service;



@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final OrganizationRepository organizationRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                              OrganizationRepository organizationRepository) {
        this.departmentRepository = departmentRepository;
        this.organizationRepository = organizationRepository;
    }

    public List<DepartmentResponse> list(AuthenticatedUser user) {
        return departmentRepository.findByOrganizationIdOrderByNameAsc(user.getOrganizationId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public DepartmentResponse create(AuthenticatedUser admin, DepartmentRequest request) {
        departmentRepository.findByOrganizationIdAndNameIgnoreCase(admin.getOrganizationId(), request.getName())
                .ifPresent(d -> {
                    throw new DepartmentAlreadyExistsException("A department with this name already exists");
                });

        Organization organization = organizationRepository.findById(admin.getOrganizationId())
                .orElseThrow(() -> new DepartmentNotFoundException("Organization not found"));

        Department department = new Department();
        department.setOrganization(organization);
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setActive(true);

        return toResponse(departmentRepository.save(department));
    }

    @Transactional
    public DepartmentResponse update(AuthenticatedUser admin, UUID departmentId, DepartmentRequest request) {
        Department department = requireOwnedDepartment(admin, departmentId);

        departmentRepository.findByOrganizationIdAndNameIgnoreCase(admin.getOrganizationId(), request.getName())
                .filter(d -> !d.getId().equals(departmentId))
                .ifPresent(d -> {
                    throw new DepartmentAlreadyExistsException("A department with this name already exists");
                });

        department.setName(request.getName());
        department.setDescription(request.getDescription());

        return toResponse(departmentRepository.save(department));
    }

    @Transactional
    public void deactivate(AuthenticatedUser admin, UUID departmentId) {
        Department department = requireOwnedDepartment(admin, departmentId);
        department.setActive(false);
        departmentRepository.save(department);
    }

    private Department requireOwnedDepartment(AuthenticatedUser user, UUID departmentId) {
        return departmentRepository.findByIdAndOrganizationId(departmentId, user.getOrganizationId())
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
    }

    private DepartmentResponse toResponse(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getDescription(),
                department.isActive(),
                department.getCreatedAt()
        );
    }
}
