package com.example.safepass_api.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.safepass_api.domain.enums.SystemUserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "system_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUser {
    @Id
    private String id; // Maps to TEXT/UUID

    // --- INLINED CREDENTIAL FIELDS ---
    @Column(nullable = false)
    private String firstName;
    
    private String middleName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true)
    private String contactNo;
    // ---------------------------------

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SystemUserRole role;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Relationships (Relationships where this user is the reviewer/issuer)
    @OneToMany(mappedBy = "reviewer", cascade = CascadeType.ALL)
    private List<StakeHolderApproval> stakeholderApprovals;



    @OneToMany(mappedBy = "issuer", cascade = CascadeType.ALL)
    private List<StakeHolderQrCode> qrCodesIssued;

    @OneToMany(mappedBy = "systemUser", cascade = CascadeType.ALL)
    private List<EntryLog> entryLogs;

    // Renamed to match the explicit relation in Prisma: @relation("VerifiedBy")
    @PrePersist
    protected void onCreate() {
        this.id = java.util.UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}