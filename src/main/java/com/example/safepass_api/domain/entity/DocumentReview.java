package com.example.safepass_api.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.safepass_api.domain.enums.DocumentStatus;

@Entity
@Table(name = "document_review")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentReview {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DocumentStatus status;
    
    @Column(nullable = true)
    private String remarks;
    
    @Column(nullable = false)
    private LocalDateTime reviewedAt = LocalDateTime.now();

    // Foreign Key to Documents
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documentId", nullable = false)
    private Documents document;

    // Foreign Key to SystemUser (reviewedBy)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewedBy", nullable = false)
    private SystemUser reviewer;

    @PrePersist
    protected void onCreate() {
        this.id = java.util.UUID.randomUUID().toString();
    }
}