
package com.example.leaveittous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leaveittous.entity.Faculty;
import com.example.leaveittous.repository.FacultyRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyRepository facultyRepository;

    // ✔️ Find Faculty by Email (used during login)
    public Faculty findByEmail(String email) {
        return facultyRepository.findByEmailId(email);
    }

    // ✔️ Save Faculty (optional – useful for future admin panel)
    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    // ✔️ Find Faculty by ID (useful in dashboard and relationships)
    public Faculty findById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }
}

