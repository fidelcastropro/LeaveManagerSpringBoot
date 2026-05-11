package com.example.leaveittous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.entity.Warden;
import com.example.leaveittous.service.WardenDashboardService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WardenDashboardController {

    @Autowired
    private WardenDashboardService wardenDashboardService;

    @GetMapping("/warden/dashboard")
    public String showWardenDashboard(HttpSession session, Model model) {
        Warden loggedWarden = (Warden) session.getAttribute("loggedWarden");

        if (loggedWarden == null) {
            return "redirect:/login";
        }

        List<LeaveRequest> leaveRequests = wardenDashboardService
                .getLeaveRequestsForWarden(loggedWarden.getBlock());

        model.addAttribute("warden", loggedWarden);
        model.addAttribute("leaveRequests", leaveRequests);
        return "wardenDashboard";
    }

    @PostMapping("/warden/approve-leave")
    public String approveLeave(@RequestParam("leaveId") Long leaveId,
                               RedirectAttributes redirectAttributes, HttpSession session) {
        Warden loggedWarden = (Warden) session.getAttribute("loggedWarden");
        wardenDashboardService.approveLeave(leaveId, loggedWarden.getName());
        redirectAttributes.addFlashAttribute("successMessage", "Leave approved successfully.");
        return "redirect:/warden/dashboard";
    }

    @PostMapping("/warden/reject-leave")
    public String rejectLeave(@RequestParam("leaveId") Long leaveId,
                              RedirectAttributes redirectAttributes, HttpSession session) {
        Warden loggedWarden = (Warden) session.getAttribute("loggedWarden");
        wardenDashboardService.rejectLeave(leaveId, loggedWarden.getName());
        redirectAttributes.addFlashAttribute("errorMessage", "Leave rejected.");
        return "redirect:/warden/dashboard";
    }
}