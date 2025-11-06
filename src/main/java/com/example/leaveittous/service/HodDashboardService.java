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
public class HodDashboardService {

    @Autowired
    private RelationshipRepository relationshipRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LeaveRepository leaveRepository;

    /**
     * Fetches all leave requests that have been approved by faculty (FA)
     * for classes handled by this HOD.
     */
    public List<LeaveRequest> getLeaveRequestsForHod(Long hodId) {

        // Step 1: Get all classes handled by this HOD
        List<Relationship> relationships = relationshipRepository.findByHod_HodId(hodId);
        List<String> classIds = relationships.stream()
                .map(Relationship::getClassId)
                .collect(Collectors.toList());

        if (classIds.isEmpty()) return new ArrayList<>();

        // Step 2: Get all students in those classes
        List<Student> students = studentRepository.findByClassIdIn(classIds);
        List<String> rollNos = students.stream()
                .map(Student::getRollNo)
                .collect(Collectors.toList());

        if (rollNos.isEmpty()) return new ArrayList<>();

        // Step 3: Get all faculty-approved (FA) leave requests
        return leaveRepository.findByStudent_RollNoInAndStatus(rollNos, "FA");
    }

    /**
     * Updates the leave status to "HA" (HOD Approved)
     */
    public void approveLeave(Long leaveId) {
        LeaveRequest request = leaveRepository.findByLeaveId(leaveId);
        if (request != null) {
            request.setStatus("HA");
            leaveRepository.save(request);
        }
    }

    /**
     * Updates the leave status to "HR" (HOD Rejected)
     */
    public void rejectLeave(Long leaveId) {
        LeaveRequest request = leaveRepository.findByLeaveId(leaveId);
        if (request != null) {
            request.setStatus("HR");
            leaveRepository.save(request);
        }
    }
}
