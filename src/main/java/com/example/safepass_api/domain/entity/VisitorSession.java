package com.example.safepass_api.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.safepass_api.domain.enums.VisitorSessionStatus;

@Entity
@Table(name = "visitor_session")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitorSession {
    @Id
    private String id;

    @Column(nullable = false)
    private String purposeOfVisit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisitorSessionStatus status = VisitorSessionStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime passExpiry;

    @Column(nullable = false)
    private Boolean isVerified = false;

    private LocalDateTime verifiedAt;

    // Foreign Key to Visitor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitorId", nullable = false)
    private Visitor visitor;

    // Foreign Key to PassInventory (One-to-one, owning side)
    @OneToOne
    @JoinColumn(name = "passInventoryId", referencedColumnName = "id", unique = true, nullable = false)
    private PassInventory passInventory;

    // Foreign Key to SystemUser (VerifiedBy) - Nullable, ON DELETE SET NULL
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verifiedBy", referencedColumnName = "id")
    private SystemUser verifier;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // Relationships
    @OneToMany(mappedBy = "visitorSession", cascade = CascadeType.ALL)
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