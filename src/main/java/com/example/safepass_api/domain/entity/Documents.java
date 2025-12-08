package com.example.safepass_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.safepass_api.domain.enums.DocumentType;

@Entity
@Table(name = "documents", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"stakeHolderId", "documentType"})
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Documents {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentType documentType;

    @Column(nullable = false)
    private String documentUrl;

    // Foreign Key to StakeHolder
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeHolderId", nullable = false)
    private StakeHolder stakeHolder;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<DocumentReview> documentReviews;

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