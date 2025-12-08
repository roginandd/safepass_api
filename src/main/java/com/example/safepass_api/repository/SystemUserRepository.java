package com.example.safepass_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.safepass_api.domain.entity.SystemUser;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long>{
    SystemUser findByUsername(String username);
    
}
