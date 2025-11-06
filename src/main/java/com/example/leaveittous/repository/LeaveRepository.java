package com.example.leaveittous.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.entity.Student;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStudent(Student student);
    List<LeaveRequest> findByStudent_RollNo(String rollNo);
    List<LeaveRequest> findByStatus(String status);
    // List<LeaveRequest> findByRollNoIn(List<String> rollNos);
    List<LeaveRequest> findByStudent_RollNoIn(List<String> rollNos);
    LeaveRequest findByLeaveId(Long leaveId);
    List<LeaveRequest> findByStudent_RollNoInAndStatus(List<String> rollNos, String status);

    List<LeaveRequest> findByStudentEmailIdOrderByLeaveIdDesc(String emailId);

}

