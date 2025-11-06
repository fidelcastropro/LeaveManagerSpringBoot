package com.example.leaveittous.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "roll_no", nullable = false, unique = true)
    private String rollNo;

    private String name;
    private String classId;
    private String mobileNo;
    private String parentMobile1;
    private String parentMobile2;

    @Column(unique = true)
    private String emailId;

    private String photo;

    @Enumerated(EnumType.STRING)
    private StudentType studentType; // HOSTELLER or DAY_SCHOLAR

    private String hostelBlock;     // for hosteller
    private String roomNo;          // for hosteller
    private String residentAddress; // for day scholar
}

