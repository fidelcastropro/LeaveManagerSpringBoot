package com.example.leaveittous.repository;


import com.example.leaveittous.entity.Warden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WardenRepository extends JpaRepository<Warden, Long> {
    Warden findByEmailId(String emailId);
}
