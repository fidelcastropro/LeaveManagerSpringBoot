package com.example.leaveittous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.leaveittous.entity.Faculty;
import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.service.FacultyDashboardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class FacultyDashboardController {

    @Autowired
    private FacultyDashboardService facultyDashboardService;

    @GetMapping("/faculty/dashboard")
    public String showFacultyDashboard(HttpSession session, Model model) {

        Faculty loggedFaculty = (Faculty) session.getAttribute("loggedFaculty");

        if (loggedFaculty == null) {
            return "redirect:/login";
        }

        List<LeaveRequest> leaveRequests = facultyDashboardService
                .getLeaveRequestsForFaculty(loggedFaculty.getFacultyId());

        model.addAttribute("faculty", loggedFaculty);
        model.addAttribute("leaveRequests", leaveRequests);

        return "facultyDashboard"; // Thymeleaf template name
    }


    @PostMapping("/faculty/approve-leave")
    public String approveLeave(@RequestParam("leaveId") Long leaveId,RedirectAttributes redirectAttributes) {
        facultyDashboardService.approveLeave(leaveId);
        redirectAttributes.addFlashAttribute("successMessage", 
            "Leave Approved Successfully for Leave ID: " + leaveId);
        return "redirect:/faculty/dashboard";
    }

    @PostMapping("/faculty/reject-leave")
    public String rejectLeave(@RequestParam("leaveId") Long leaveId,RedirectAttributes redirectAttributes) {
        facultyDashboardService.rejectLeave(leaveId);
        redirectAttributes.addFlashAttribute("errorMessage", 
            "Leave Rejected for Leave ID: " + leaveId);
        return "redirect:/faculty/dashboard";
    }

}
