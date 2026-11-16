package com.flownexa.auth.domain.model;


import com.flownexa.business.domain.model.Department;
import com.flownexa.auth.domain.enums.Role;
import com.flownexa.auth.domain.enums.UserStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status;
    
    @Column(name = "requested_at", nullable = false, updatable = false)
    private LocalDateTime requestedAt;
    
    @Column(name = "decided_at")
    private LocalDateTime decidedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "decided_by_user_id")
    private Users decidedBy;
    
    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(length = 30)
    private String phone;

    @Column(name = "job_title", length = 100)
    private String jobTitle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    @PrePersist
    protected void onCreate() {
        this.requestedAt = LocalDateTime.now();
    }
    
}