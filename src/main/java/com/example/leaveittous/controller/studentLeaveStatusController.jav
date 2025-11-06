package com.example.leaveittous.controller;

import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.repository.LeaveRequestRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StudentLeaveStatusController {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @GetMapping("/student/leave-status")
    public String showLeaveStatus(Model model, HttpSession session) {
        // ✅ Assuming student email is stored in session when logging in
        String email = (String) session.getAttribute("email");

        if (email == null) {
            model.addAttribute("error", "Please log in first!");
            return "redirect:/student/login";
        }

        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByStudentEmailOrderByLeaveIdDesc(email);

        List<Map<String, Object>> leaves = leaveRequests.stream().map(leave -> Map.of(
                "id", leave.getLeaveId(),
                "fromDate", leave.getFromDate(),
                "toDate", leave.getToDate(),
                "status", leave.getStatus(),
                "reasonType", leave.getReasonType(),
                "purpose", leave.getPurpose()
        )).collect(Collectors.toList());

        model.addAttribute("leaveRequests", leaves);
        return "studentLeaveStatus"; // corresponds to studentLeaveStatus.html
    }
}
