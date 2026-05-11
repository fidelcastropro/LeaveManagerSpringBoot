package com.example.leaveittous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.leaveittous.entity.Credentials;
import com.example.leaveittous.repository.CredentialsRepository;

@Service
public class CredentialService {

    @Autowired
    private CredentialsRepository credentialRepository;

    public Credentials login(String email, String password) {
        return credentialRepository.findByEmailIdAndPassword(email, password);
    }
}

