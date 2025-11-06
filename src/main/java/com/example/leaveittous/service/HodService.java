package com.example.leaveittous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leaveittous.entity.HOD;
import com.example.leaveittous.repository.HODRepository;

@Service
public class HodService {

    @Autowired
    HODRepository hodRepository;

    public HOD findByEmail(String email){
        return hodRepository.findByEmailId(email);
    }
    
}
