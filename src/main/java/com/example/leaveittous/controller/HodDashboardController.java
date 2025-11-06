package com.example.leaveittous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.leaveittous.entity.HOD;
import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.service.HodDashboardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HodDashboardController {

    @Autowired
    private HodDashboardService hodDashboardService;

    @GetMapping("/hod/dashboard")
    public String showHodDashboard(HttpSession session, Model model) {

        HOD loggedHod = (HOD) session.getAttribute("loggedHod");

        if (loggedHod == null) {
            return "redirect:/login";
        }

        List<LeaveRequest> leaveRequests = hodDashboardService
                .getLeaveRequestsForHod(loggedHod.getHodId());

        model.addAttribute("hod", loggedHod);
        model.addAttribute("leaveRequests", leaveRequests);

        return "hodDashboard"; // Thymeleaf template name
    }

    @PostMapping("/hod/approve-leave")
    public String approveLeave(@RequestParam("leaveId") Long leaveId,
                               RedirectAttributes redirectAttributes) {
        hodDashboardService.approveLeave(leaveId);
        redirectAttributes.addFlashAttribute("successMessage",
                "Leave Approved Successfully for Leave ID: " + leaveId);
        return "redirect:/hod/dashboard";
    }

    @PostMapping("/hod/reject-leave")
    public String rejectLeave(@RequestParam("leaveId") Long leaveId,
                              RedirectAttributes redirectAttributes) {
        hodDashboardService.rejectLeave(leaveId);
        redirectAttributes.addFlashAttribute("errorMessage",
                "Leave Rejected for Leave ID: " + leaveId);
        return "redirect:/hod/dashboard";
    }
}
