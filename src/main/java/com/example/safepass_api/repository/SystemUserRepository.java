package com.example.safepass_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.safepass_api.domain.entity.SystemUser;

public interface SystemUserRepository extends JpaRepository<SystemUser, String>{
    Optional<SystemUser> findByUsername(String username);

}
