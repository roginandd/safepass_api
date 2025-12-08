package com.example.safepass_api.domain.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stake_holder_approval")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StakeHolderApproval {
    @Id
    private String id;

    @Column(nullable = true)
    private String remarks;
    
    @Column(nullable = false)
    private LocalDateTime reviewedAt = LocalDateTime.now();

    // Foreign Key to StakeHolder
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stakeHolderId", nullable = false)
    private StakeHolder stakeHolder;

    // Foreign Key to SystemUser (reviewedBy)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewedBy", nullable = false)
    private SystemUser reviewer;

    @PrePersist
    protected void onCreate() {
        this.id = java.util.UUID.randomUUID().toString();
    }
}