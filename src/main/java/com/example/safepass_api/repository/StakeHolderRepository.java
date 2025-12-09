package com.example.safepass_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.safepass_api.domain.entity.StakeHolder;

@Repository
public interface StakeHolderRepository extends JpaRepository<StakeHolder, String> {
    boolean existsByEmail(String email);
    boolean existsByContactNo(String contactNo);
}
