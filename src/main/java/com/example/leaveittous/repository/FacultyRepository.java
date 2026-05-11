package com.example.leaveittous.repository;

import com.example.leaveittous.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findByEmailId(String emailId);
}

