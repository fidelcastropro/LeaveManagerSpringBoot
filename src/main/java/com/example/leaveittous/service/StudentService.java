package com.example.leaveittous.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leaveittous.entity.Student;
import com.example.leaveittous.repository.StudentRepository;

@Service
public class StudentService {
    @Autowired private StudentRepository studentRepository;

    public Student findByEmail(String email) {
        return studentRepository.findByEmailId(email).orElse(null);
    }
    public Student findByRollNo(String rollNo){ return studentRepository.findById(rollNo).orElse(null); }
}

