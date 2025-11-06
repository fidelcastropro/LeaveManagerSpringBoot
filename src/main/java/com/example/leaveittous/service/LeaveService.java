package com.example.leaveittous.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.repository.LeaveRepository;


@Service
public class LeaveService {
    @Autowired private LeaveRepository leaveRepository;

    public LeaveRequest saveLeaveRequest(LeaveRequest lr) {
        lr.setAppliedTime(LocalDateTime.now());
        lr.setStatus("S");
        return leaveRepository.save(lr);
    }

    public List<LeaveRequest> getLeaveRequestsByRollNo(String rollNo) {
        return leaveRepository.findByStudent_RollNo(rollNo);
    }
    
}

