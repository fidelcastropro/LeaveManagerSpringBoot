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
                               RedirectAttributes redirectAttributes, HttpSession session) {
        HOD loggedHod = (HOD) session.getAttribute("loggedHod");
        hodDashboardService.approveLeave(leaveId, loggedHod.getName());
        redirectAttributes.addFlashAttribute("successMessage", "Leave approved successfully.");
        redirectAttributes.addFlashAttribute("selectedLeaveId", leaveId);
        return "redirect:/hod/dashboard";
    }

    @PostMapping("/hod/reject-leave")
    public String rejectLeave(@RequestParam("leaveId") Long leaveId,
                              RedirectAttributes redirectAttributes, HttpSession session) {
        HOD loggedHod = (HOD) session.getAttribute("loggedHod");
        hodDashboardService.rejectLeave(leaveId, loggedHod.getName());
        redirectAttributes.addFlashAttribute("errorMessage", "Leave rejected.");
        redirectAttributes.addFlashAttribute("selectedLeaveId", leaveId);
        return "redirect:/hod/dashboard";
    }
}
