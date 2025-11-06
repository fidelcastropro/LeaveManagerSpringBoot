package com.example.leaveittous.repository;

import com.example.leaveittous.entity.HOD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HODRepository extends JpaRepository<HOD, Long> {
    HOD findByEmailId(String emailId);
}

