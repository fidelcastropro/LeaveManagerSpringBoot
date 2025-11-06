package com.example.leaveittous.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "relationship")
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String classId;

    @ManyToOne
    @JoinColumn(name = "faculty_id", referencedColumnName = "faculty_id")
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "hod_id", referencedColumnName = "hod_id")
    private HOD hod;
}
