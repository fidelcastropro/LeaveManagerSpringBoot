package com.example.leaveittous.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.leaveittous.entity.LeaveRequest;
import com.example.leaveittous.entity.Student;
import com.example.leaveittous.service.LeaveService;
import com.example.leaveittous.service.StudentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class LeaveController {

    @Autowired
    private LeaveService leaveRequestService;

    @Autowired
    private StudentService studentService;

    private final String uploadDir = "uploads/";

    @GetMapping("/leave-form")
    public String showLeaveForm(@RequestParam("type") String studentType,
                                @RequestParam("rollNo") String rollNo,
                                Model model,
                                HttpSession session) {

        if (session.getAttribute("loggedStudent") == null) {
            return "redirect:/login";
        }

        Student student = studentService.findByRollNo(rollNo);
        if (student == null) {
            model.addAttribute("errorMsg", "Student not found!");
            return "errorPage";
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStudent(student);

        model.addAttribute("leaveRequest", leaveRequest);
        model.addAttribute("studentType", studentType);
        model.addAttribute("student", student);

        return "studentLeavePage";
    }

    @PostMapping("/submit-leave")
    public String submitLeaveForm(@ModelAttribute LeaveRequest leaveRequest,
                                  @RequestParam(value = "file", required = false) MultipartFile file,
                                  Model model) {
        try {
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());
                leaveRequest.setFilePath(filePath.toString());
            }

            if (leaveRequest.getStudent() != null && leaveRequest.getStudent().getRollNo() != null) {
                Student existingStudent = studentService.findByRollNo(leaveRequest.getStudent().getRollNo());
                if (existingStudent == null) {
                    model.addAttribute("errorMsg", "Student not found!");
                    return "studentLeavePage";
                }
                leaveRequest.setStudent(existingStudent);
            } else {
                model.addAttribute("errorMsg", "Invalid student information in form!");
                return "studentLeavePage";
            }

            leaveRequest.setAppliedTime(LocalDateTime.now());
            leaveRequest.setStatus("S");

            leaveRequestService.saveLeaveRequest(leaveRequest);

            model.addAttribute("successMsg", "Leave request submitted successfully!");
            model.addAttribute("leaveRequest", new LeaveRequest());
            return "studentLeavePage";

        } catch (IOException e) {
            model.addAttribute("errorMsg", "File upload failed: " + e.getMessage());
            return "studentLeavePage";
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Error while saving leave: " + e.getMessage());
            return "studentLeavePage";
        }
    }
}
