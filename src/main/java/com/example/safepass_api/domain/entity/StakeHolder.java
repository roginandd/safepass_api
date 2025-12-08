package com.example.safepass_api.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.example.safepass_api.domain.enums.StakeHolderStatus;

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

import lombok.*;

@Entity
@Table(name = "stake_holder")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StakeHolder {
    @Id
    private String id;

    // --- INLINED CREDENTIAL FIELDS ---
    @Column(nullable = false)
    private String firstName;
    
    private String middleName;
    
    @Column(nullable = false)
    private String lastName;
    
    // Unique constraint will be created via @Table uniqueConstraints or automatically by JPA
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false, unique = true)
    private String contactNo;
    // ---------------------------------

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StakeHolderStatus status = StakeHolderStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "stakeHolder", cascade = CascadeType.ALL)
    private List<Documents> documents;

    @OneToMany(mappedBy = "stakeHolder", cascade = CascadeType.ALL)
    private List<StakeHolderApproval> approvals;

    @OneToMany(mappedBy = "stakeHolder", cascade = CascadeType.ALL)
    private List<StakeHolderQrCode> qrCodes;

    @OneToMany(mappedBy = "stakeHolder", cascade = CascadeType.ALL)
    private List<EntryLog> entryLogs;

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