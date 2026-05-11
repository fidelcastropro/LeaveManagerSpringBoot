package com.example.leaveittous.controller;

import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.repository.LeaveRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StudentLeaveStatusController {

    @Autowired
    private LeaveRepository leaveRepository;

    @GetMapping("/student/leave-status")
    public String showLeaveStatus(Model model, HttpSession session) {
        String email = (String) session.getAttribute("email");

        if (email == null) {
            return "redirect:/login";
        }

        List<LeaveRequest> leaveRequests = leaveRepository.findByStudentEmailIdOrderByLeaveIdDesc(email);
        model.addAttribute("leaveRequests", leaveRequests);

        // Pass student from session into model for the back button link
        com.example.leaveittous.entity.Student student =
            (com.example.leaveittous.entity.Student) session.getAttribute("loggedStudent");
        model.addAttribute("loggedStudent", student);
        model.addAttribute("studentType", student != null ? student.getStudentType().name() : "DAY_SCHOLAR");

        return "studentLeaveStatus";
    }
}
