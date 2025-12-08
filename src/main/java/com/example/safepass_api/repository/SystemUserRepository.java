package com.example.safepass_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.safepass_api.domain.entity.SystemUser;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, String>{
    Optional<SystemUser> findByUsername(String username);

}
