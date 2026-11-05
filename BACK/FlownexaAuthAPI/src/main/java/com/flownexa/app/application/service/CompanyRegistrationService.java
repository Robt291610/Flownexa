package com.flownexa.app.application.service;

import com.flownexa.app.adapter.in.web.dto.RegisterCompanyRequest;
import com.flownexa.app.adapter.in.web.dto.RegisterCompanyResponse;
import com.flownexa.app.adapter.out.persistance.OrganizationRepository;
import com.flownexa.app.adapter.out.persistance.UsersRepository;
import com.flownexa.app.domain.enums.Role;
import com.flownexa.app.domain.exception.CompanyAlreadyExistsException;
import com.flownexa.app.domain.exception.EmailAlreadyExistsException;
import com.flownexa.app.domain.model.Organization;
import com.flownexa.app.domain.model.Users;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CompanyRegistrationService {

    private final OrganizationRepository organizationRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanyRegistrationService(OrganizationRepository organizationRepository,
                                       UsersRepository usersRepository,
                                       PasswordEncoder passwordEncoder) {
        this.organizationRepository = organizationRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public RegisterCompanyResponse register(RegisterCompanyRequest request) {
        organizationRepository.findByName(request.getCompanyName())
                .ifPresent(o -> {
                    throw new CompanyAlreadyExistsException("Company name is already registered");
                });

        usersRepository.findByEmail(request.getAdminEmail())
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException("Email is already registered");
                });

        Organization organization = new Organization();
        organization.setName(request.getCompanyName());
        organization.setActive(true);
        Organization savedOrganization = organizationRepository.save(organization);

        Users admin = new Users();
        admin.setEmail(request.getAdminEmail());
        admin.setPasswordHash(passwordEncoder.encode(request.getAdminPassword()));
        admin.setActive(true);
        admin.setOrganization(savedOrganization);
        admin.setRole(Role.ADMIN);
        Users savedAdmin = usersRepository.save(admin);

        return new RegisterCompanyResponse(
                savedOrganization.getId(),
                savedOrganization.getName(),
                savedAdmin.getId(),
                savedAdmin.getEmail()
        );
    }
}
