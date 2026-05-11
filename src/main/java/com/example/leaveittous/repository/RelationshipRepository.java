package com.example.leaveittous.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.leaveittous.entity.Relationship;

public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    Relationship findByClassId(String classId);
    // List<Relationship> findByFacultyId(Long facultyId);
    List<Relationship> findByFaculty_FacultyId(Long facultyId);
    List<Relationship> findByHod_HodId(Long hodId);
}

