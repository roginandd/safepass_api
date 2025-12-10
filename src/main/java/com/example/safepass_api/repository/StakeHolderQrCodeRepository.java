package com.example.safepass_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.safepass_api.domain.entity.StakeHolderQrCode;

@Repository
public interface StakeHolderQrCodeRepository extends JpaRepository<StakeHolderQrCode, Long>{
    Optional<StakeHolderQrCode> findByStakeHolderId(String stakeHolderId);
    boolean existsByStakeHolderId(String stakeHolderId);
}
