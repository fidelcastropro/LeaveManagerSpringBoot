package com.example.leaveittous.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.entity.Relationship;
import com.example.leaveittous.entity.Student;
import com.example.leaveittous.repository.LeaveRepository;
import com.example.leaveittous.repository.RelationshipRepository;
import com.example.leaveittous.repository.StudentRepository;

@Service
public class FacultyDashboardService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    public List<LeaveRequest> getLeaveRequestsForFaculty(Long facultyId) {

        // Step 1: Get all classes handled by this faculty
        List<Relationship> rels = relationshipRepository.findByFaculty_FacultyId(facultyId);
        List<String> classIds = rels.stream()
                                    .map(Relationship::getClassId)
                                    .collect(Collectors.toList());

        if (classIds.isEmpty()) return new ArrayList<>();

        // Step 2: Get all students in those classes
        List<Student> students = studentRepository.findByClassIdIn(classIds);
        List<String> rollNos = students.stream()
                                       .map(Student::getRollNo)
                                       .collect(Collectors.toList());

        if (rollNos.isEmpty()) return new ArrayList<>();

        // Step 3: Get all leave requests of those students
        return leaveRepository.findByStudent_RollNoIn(rollNos);
    }

    public void approveLeave(Long leaveId) {
        LeaveRequest request = leaveRepository.findByLeaveId(leaveId);
        if (request != null) {
            request.setStatus("FA"); // Faculty Approved
            leaveRepository.save(request);
        }
    }

    public void rejectLeave(Long leaveId) {
        LeaveRequest request = leaveRepository.findByLeaveId(leaveId);
        if (request != null) {
            request.setStatus("FR"); // Faculty Rejected
            leaveRepository.save(request);
        }
    }
}

