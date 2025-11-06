package com.example.leaveittous.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.leaveittous.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByEmailId(String emailId);
    List<Student> findByClassIdIn(List<String> classIds);
}
