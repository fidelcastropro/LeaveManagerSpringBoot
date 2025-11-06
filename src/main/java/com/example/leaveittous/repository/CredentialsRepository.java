package com.example.leaveittous.repository;

import com.example.leaveittous.entity.Credentials;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CredentialsRepository extends JpaRepository<Credentials, String> {
    Credentials findByEmailId(String emailId);
    Credentials findByEmailIdAndPassword(String emailId, String password);
}

