// package com.example.leaveittous.controller;

// import java.io.IOException;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.time.LocalDateTime;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

// import com.example.leaveittous.entity.LeaveRequest;
// import com.example.leaveittous.entity.Student;
// import com.example.leaveittous.service.LeaveService;
// import com.example.leaveittous.service.StudentService;

// @Controller
// @RequestMapping("/student")
// public class LeaveController {

//     @Autowired
//     private LeaveService leaveRequestService;

//     @Autowired
//     private StudentService studentService;


//     private final String uploadDir = "uploads/";

//     @GetMapping("/leave-form")
//     public String showLeaveForm(@RequestParam("type") String studentType,
//                                 @RequestParam("rollNo") String rollNo,
//                                 Model model) {
//         model.addAttribute("leaveRequest", new LeaveRequest());
//         model.addAttribute("studentType", studentType); // HOSTELLER or DAY_SCHOLAR
//         model.addAttribute("rollNo", rollNo);
//         return "studentLeavePage";
//     }


//     @PostMapping("/submit-leave")
//     public String submitLeaveForm(@ModelAttribute LeaveRequest leaveRequest,
//                               @RequestParam("file") MultipartFile file,
//                               Model model) {
//         try {
//             if (!file.isEmpty()) {
//                 String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//                 Path filePath = Paths.get(uploadDir + fileName);
//                 Files.createDirectories(filePath.getParent());
//                 Files.write(filePath, file.getBytes());
//                 leaveRequest.setFilePath(filePath.toString());
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         if (leaveRequest.getStudent() != null && leaveRequest.getStudent().getRollNo() != null) {
//             Student existingStudent = studentService.findByRollNo(leaveRequest.getStudent().getRollNo());
//                 if (existingStudent != null) {
//                     leaveRequest.setStudent(existingStudent);
//                 } else {
//                     model.addAttribute("errorMsg", "Student not found!");
//                     return "studentLeavePage";
//                 }
//             }
    

//         leaveRequest.setAppliedTime(LocalDateTime.now());
//         leaveRequest.setStatus("S"); // initial status

//         leaveRequestService.saveLeaveRequest(leaveRequest);
//         System.out.println("✅ Leave saved successfully");


//         model.addAttribute("successMsg", "Leave request submitted successfully!");
//         return "studentLeavePage";
// }

// }



package com.example.leaveittous.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.example.leaveittous.repository.LeaveRepository;
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

    /**
     * ✅ Displays the Leave Form for a Student
     * 
     *
     */


    @Autowired
    private LeaveRepository leaveRequestRepository;

    @GetMapping("/leave-status")
    public String showLeaveStatus(Model model, HttpSession session) {
        // ✅ Assuming student email is stored in session when logging in
        String email = (String) session.getAttribute("email");

        if (email == null) {
            model.addAttribute("error", "Please log in first!");
            return "redirect:/student/login";
        }

        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByStudentEmailIdOrderByLeaveIdDesc(email);

        List<Map<String, Object>> leaves = leaveRequests.stream().map(leave -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", leave.getLeaveId());
            map.put("fromDate", leave.getFromDate());
            map.put("toDate", leave.getToDate());
            map.put("status", leave.getStatus());
            map.put("reasonType", leave.getReasonType());
            map.put("purpose", leave.getPurpose());
            return map;
        }).collect(Collectors.toList());

        model.addAttribute("leaveRequests", leaves);
        return "studentLeaveStatus"; // corresponds to studentLeaveStatus.html
    }


    @GetMapping("/leave-form")
    public String showLeaveForm(@RequestParam("type") String studentType,
                                @RequestParam("rollNo") String rollNo,
                                Model model) {

        // ✅ Fetch the student from DB
        Student student = studentService.findByRollNo(rollNo);
        if (student == null) {
            model.addAttribute("errorMsg", "Student not found!");
            return "errorPage"; // Optional error page
        }

        // ✅ Create a new LeaveRequest and set student
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setStudent(student);

        // ✅ Pass required attributes to the view
        model.addAttribute("leaveRequest", leaveRequest);
        model.addAttribute("studentType", studentType);
        model.addAttribute("student", student);

        return "studentLeavePage"; // your Thymeleaf file
    }

    /**
     * ✅ Handles the submission of a leave form
     */
    @PostMapping("/submit-leave")
    public String submitLeaveForm(@ModelAttribute LeaveRequest leaveRequest,
                                  @RequestParam(value = "file", required = false) MultipartFile file,
                                  Model model) {
        try {
            // ✅ Handle file upload
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());
                leaveRequest.setFilePath(filePath.toString());
            }

            // ✅ Ensure the student exists in DB
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

            // ✅ Set system-managed fields
            leaveRequest.setAppliedTime(LocalDateTime.now());
            leaveRequest.setStatus("S"); // Submitted

            // ✅ Save to DB
            leaveRequestService.saveLeaveRequest(leaveRequest);
            System.out.println("✅ Leave saved successfully for " + leaveRequest.getStudent().getName());

            // ✅ Success message
            model.addAttribute("successMsg", "Leave request submitted successfully!");
            model.addAttribute("leaveRequest", new LeaveRequest());
            return "studentLeavePage";

        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "File upload failed: " + e.getMessage());
            return "studentLeavePage";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMsg", "Error while saving leave: " + e.getMessage());
            return "studentLeavePage";
        }
    }
    
}
