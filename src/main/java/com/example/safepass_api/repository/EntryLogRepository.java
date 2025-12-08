package com.example.safepass_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.safepass_api.domain.entity.EntryLog;

@Repository
public interface EntryLogRepository extends JpaRepository<EntryLog, String>{

}
