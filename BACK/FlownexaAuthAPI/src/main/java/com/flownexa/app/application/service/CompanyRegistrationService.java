package com.flownexa.app.application.service;

import com.flownexa.app.adapter.in.web.dto.CreateCompanyRequest;
import com.flownexa.app.adapter.in.web.dto.JoinCompanyRequest;
import com.flownexa.app.adapter.in.web.dto.RegisterCompanyResponse;
import com.flownexa.app.adapter.out.email.interfaces.EmailService;
import com.flownexa.app.adapter.out.persistance.OrganizationRepository;
import com.flownexa.app.adapter.out.persistance.UsersRepository;
import com.flownexa.app.domain.enums.Role;
import com.flownexa.app.domain.enums.UserStatus;
import com.flownexa.app.domain.exception.CompanyAlreadyExistsException;
import com.flownexa.app.domain.exception.CompanyNotFoundException;
import com.flownexa.app.domain.exception.EmailAlreadyExistsException;
import com.flownexa.app.domain.model.Organization;
import com.flownexa.app.domain.model.Users;
import jakarta.transaction.Transactional;
import java.util.List;
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
    public RegisterCompanyResponse createCompany(CreateCompanyRequest request) {
        usersRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException("Email is already registered");
                });

        organizationRepository.findByTaxId(request.getTaxId())
                .ifPresent(o -> {
                    throw new CompanyAlreadyExistsException("A company with this Tax ID is already registered");
                });

        organizationRepository.findByName(request.getCompanyName())
                .ifPresent(o -> {
                    throw new CompanyAlreadyExistsException("A company with this name is already registered");
                });

        Organization organization = new Organization();
        organization.setName(request.getCompanyName());
        organization.setLegalName(request.getLegalName());
        organization.setTaxId(request.getTaxId());
        organization.setIndustry(request.getIndustry());
        organization.setCountry(request.getCountry());
        organization.setPhone(request.getCompanyPhone());
        organization.setContactEmail(request.getContactEmail());
        organization.setActive(true);
        Organization savedOrganization = organizationRepository.save(organization);

        Users admin = new Users();
        admin.setEmail(request.getEmail());
        admin.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        admin.setFullName(request.getFullName());
        admin.setPhone(request.getUserPhone());
        admin.setJobTitle(request.getJobTitle());
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
    

    @Transactional
    public RegisterCompanyResponse joinCompany(JoinCompanyRequest request) {
        usersRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new EmailAlreadyExistsException("Email is already registered");
                });

        Organization organization = organizationRepository.findByName(request.getCompanyName())
                .orElseThrow(() -> new CompanyNotFoundException("No company found with this name"));

        Users pendingUser = new Users();
        pendingUser.setEmail(request.getEmail());
        pendingUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        pendingUser.setFullName(request.getFullName());
        pendingUser.setPhone(request.getUserPhone());
        pendingUser.setJobTitle(request.getJobTitle());
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
