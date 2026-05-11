package com.example.leaveittous.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.entity.StudentType;
import com.example.leaveittous.repository.LeaveRepository;
import com.example.leaveittous.repository.StudentRepository;

@Service
public class WardenDashboardService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private StudentRepository studentRepository;

    // Wardens see HOD-approved (HA) requests from hostellers only in their block
    public List<LeaveRequest> getLeaveRequestsForWarden(String block) {
        List<String> rollNos = studentRepository
                .findByHostelBlockAndStudentType(block, StudentType.HOSTELLER)
                .stream().map(s -> s.getRollNo()).toList();

        if (rollNos.isEmpty()) return List.of();

        return leaveRepository.findByStudent_RollNoInAndStatus(rollNos, "HA");
    }

    public void approveLeave(Long leaveId, String wardenName) {
        LeaveRequest request = leaveRepository.findByLeaveId(leaveId);
        if (request != null) {
            request.setStatus("WA");
            request.setWardenName(wardenName);
            leaveRepository.save(request);
        }
    }

    public void rejectLeave(Long leaveId, String wardenName) {
        LeaveRequest request = leaveRepository.findByLeaveId(leaveId);
        if (request != null) {
            request.setStatus("WR");
            request.setWardenName(wardenName);
            leaveRepository.save(request);
        }
    }
}
