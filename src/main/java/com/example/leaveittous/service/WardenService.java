package com.example.leaveittous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leaveittous.entity.Warden;
import com.example.leaveittous.repository.WardenRepository;

@Service
public class WardenService {

    @Autowired
    private WardenRepository wardenRepository;

    public Warden findByEmail(String email) {
        return wardenRepository.findByEmailId(email);
    }
}
