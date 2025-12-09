package com.example.safepass_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.safepass_api.domain.enums.PassStatus;

@Entity
@Table(name = "pass_inventory")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassInventory {
    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String physicalCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PassStatus status = PassStatus.AVAILABLE;

    private String notes;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Relationships: One-to-one mapping

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