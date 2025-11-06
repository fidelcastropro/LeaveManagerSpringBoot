
package com.example.leaveittous.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.leaveittous.entity.Credentials;
import com.example.leaveittous.entity.Faculty;
import com.example.leaveittous.entity.HOD;
import com.example.leaveittous.entity.Student;
import com.example.leaveittous.service.CredentialService;
import com.example.leaveittous.service.FacultyService;
import com.example.leaveittous.service.HodService;
import com.example.leaveittous.service.StudentService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyService facultyService; // NEW

    @Autowired
    private HodService hodService;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("selectedUserType", "Student");
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("selectedUserType") String selectedUserType,
            Model model,
            HttpSession session) {

        // Authenticate credentials
        Credentials credential = credentialService.login(email, password);

        if (credential == null || !credential.getUserType().equalsIgnoreCase(selectedUserType)) {
            model.addAttribute("msg", "Invalid email or password");
            model.addAttribute("selectedUserType", selectedUserType);
            return "loginPage";
        }

        String userType = credential.getUserType().toLowerCase();

        switch (userType) {
            case "student":
                // fetch student by email (make sure your StudentService has this method)
                Student student = studentService.findByEmail(email);
                if (student == null) {
                    model.addAttribute("msg", "Student record not found for this email");
                    model.addAttribute("selectedUserType", selectedUserType);
                    return "loginPage";
                }

                // store student in session for later controllers (optional but common)
                session.setAttribute("email", student.getEmailId());

                session.setAttribute("loggedStudent", student);

                // determine student type string matching your LeaveController expectation:
                // if you use enum StudentType { HOSTELLER, DAY_SCHOLAR } we convert accordingly
                String typeParam = student.getStudentType() != null
                        ? student.getStudentType().name() // e.g. HOSTELLER or DAY_SCHOLAR
                        : "DAY_SCHOLAR";

                // create redirect URL to leave-form with query params
                try {
                    String encodedType = URLEncoder.encode(typeParam, "UTF-8");
                    String encodedRoll = URLEncoder.encode(student.getRollNo(), "UTF-8");
                    return "redirect:/student/leave-form?type=" + encodedType + "&rollNo=" + encodedRoll;
                } catch (UnsupportedEncodingException e) {
                    // fallback if encoding fails
                    return "redirect:/student/leave-form?type=" + typeParam + "&rollNo=" + student.getRollNo();
                }

            case "faculty":
                Faculty faculty = facultyService.findByEmail(email);
                if (faculty == null) {
                    model.addAttribute("msg", "Faculty record not found for this email");
                    model.addAttribute("selectedUserType", selectedUserType);
                    return "loginPage";
                }

                // Save faculty info in session
                session.setAttribute("loggedFaculty", faculty);

                // Redirect to faculty dashboard
                return "redirect:/faculty/dashboard";

            case "hod":
                HOD hod = hodService.findByEmail(email);
                if (hod == null) {
                    model.addAttribute("msg", "HOD record not found for this email");
                    model.addAttribute("selectedUserType", selectedUserType);
                    return "loginPage";
                }
                session.setAttribute("loggedHod", hod);
                return "redirect:/hod/dashboard";

            case "warden":
                return "redirect:/warden/dashboard";

            default:
                model.addAttribute("msg", "Unknown user type");
                model.addAttribute("selectedUserType", selectedUserType);
                return "loginPage";
        }
    }
}


