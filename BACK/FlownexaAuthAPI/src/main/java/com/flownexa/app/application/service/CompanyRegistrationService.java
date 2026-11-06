package com.flownexa.app.application.service;

import com.flownexa.app.adapter.in.web.dto.RegisterCompanyRequest;
import com.flownexa.app.adapter.in.web.dto.RegisterCompanyResponse;
import com.flownexa.app.adapter.out.email.interfaces.EmailService;
import com.flownexa.app.adapter.out.persistance.OrganizationRepository;
import com.flownexa.app.adapter.out.persistance.UsersRepository;
import com.flownexa.app.domain.enums.Role;
import com.flownexa.app.domain.enums.UserStatus;
import com.flownexa.app.domain.exception.CompanyAlreadyExistsException;
import com.flownexa.app.domain.exception.EmailAlreadyExistsException;
import com.flownexa.app.domain.model.Organization;
import com.flownexa.app.domain.model.Users;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/*There is boilerplate code in this class*/
@Service
public class CompanyRegistrationService {

    private final OrganizationRepository organizationRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public CompanyRegistrationService(OrganizationRepository organizationRepository,
                                       UsersRepository usersRepository,
                                       PasswordEncoder passwordEncoder,
                                       EmailService emailService) {
        this.organizationRepository = organizationRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public RegisterCompanyResponse register(RegisterCompanyRequest request) {
       usersRepository.findByEmail(request.getEmail())
            .ifPresent(u -> {
                throw new EmailAlreadyExistsException("Email is already registered");
            });
        
        
        Optional<Organization> existingOrg = organizationRepository.findByName(request.getCompanyName());

        if (existingOrg.isEmpty()) {
            return createCompanyWithAdmin(request);
        }

        return createJoinRequest(existingOrg.get(), request);
}
    
    private RegisterCompanyResponse createCompanyWithAdmin(RegisterCompanyRequest request) {
        Organization organization = new Organization();
        organization.setName(request.getCompanyName());
        organization.setActive(true);
        Organization savedOrganization = organizationRepository.save(organization);

        Users admin = new Users();
        admin.setEmail(request.getEmail());
        admin.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        admin.setOrganization(savedOrganization);
        admin.setRole(Role.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);
        Users savedAdmin = usersRepository.save(admin);

        return new RegisterCompanyResponse(
                savedOrganization.getId(),
                savedOrganization.getName(),
                savedAdmin.getId(),
                savedAdmin.getEmail(),
                UserStatus.ACTIVE
        );
    }
    
    private RegisterCompanyResponse createJoinRequest(Organization organization, RegisterCompanyRequest request) {

        Users pendingUser = new Users();
        pendingUser.setEmail(request.getEmail());
        pendingUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        pendingUser.setOrganization(organization);
        pendingUser.setRole(null); // assigned by admin at approval time
        pendingUser.setStatus(UserStatus.PENDING);
        Users savedPendingUser = usersRepository.save(pendingUser);

        notifyAdmins(organization, savedPendingUser);

        return new RegisterCompanyResponse(
                organization.getId(),
                organization.getName(),
                savedPendingUser.getId(),
                savedPendingUser.getEmail(),
                UserStatus.PENDING
        );    
    }
    
    private void notifyAdmins(Organization organization, Users pendingUser) {
        List<Users> admins = usersRepository.findByOrganizationAndStatus(organization, UserStatus.ACTIVE)
                .stream()
                .filter(u -> u.getRole() == Role.ADMIN)
                .toList();

        for (Users admin : admins) {
            emailService.sendJoinRequestNotification(admin.getEmail(), pendingUser.getEmail(), organization.getName());
        }
    }
    
}
