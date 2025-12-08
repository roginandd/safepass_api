package com.example.safepass_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.safepass_api.domain.enums.QrCodeStatus;

@Entity
@Table(name = "stake_holder_qr_code")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StakeHolderQrCode {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QrCodeStatus status = QrCodeStatus.ACTIVE;

    @Column(nullable = false)
    private LocalDateTime issuedAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime revokedAt;
    private String revokeReason;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Foreign Key to StakeHolder
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeHolderId", nullable = false)
    private StakeHolder stakeHolder;

    // Foreign Key to SystemUser (issuedBy)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issuedBy", nullable = false)
    private SystemUser issuer;

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