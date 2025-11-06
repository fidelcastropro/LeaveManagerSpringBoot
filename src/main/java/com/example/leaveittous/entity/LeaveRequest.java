package com.example.leaveittous.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "leave_request")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;

    @ManyToOne
    @JoinColumn(name = "roll_no", referencedColumnName = "roll_no")
    private Student student;

    private LocalTime fromTime;
    private LocalDate fromDate;
    private LocalTime toTime;
    private LocalDate toDate;

    private String filePath; // uploaded doc path

    private String status; // S, FA, FR, HA, HR

    private LocalDateTime appliedTime;

    private String reasonType;   // Personal / Medical / On Duty
    @Column(length = 500)
    private String purpose;      // Description or purpose


    // In LeaveRequest.java
private boolean stayingOutside;

public boolean isStayingOutside() {
    return stayingOutside;
}

public void setStayingOutside(boolean stayingOutside) {
    this.stayingOutside = stayingOutside;
}

}
