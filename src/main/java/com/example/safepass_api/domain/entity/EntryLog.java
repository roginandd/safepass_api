package com.example.safepass_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entry_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryLog {
    @Id
    private String id;

    @Column(nullable = false)
    private LocalDateTime entryTime = LocalDateTime.now();
    
    private LocalDateTime exitTime;
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Foreign Key to SystemUser (Logging User)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "systemUserId", nullable = false)
    private SystemUser systemUser;

    // Foreign Key to VisitorSession (Nullable, ON DELETE SET NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visitorId")
    private Visitor visitor;

    // Foreign Key to StakeHolder (Nullable, ON DELETE SET NULL)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeHolderId")
    private StakeHolder stakeHolder;

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