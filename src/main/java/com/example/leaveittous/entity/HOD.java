package com.example.leaveittous.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "hod")
public class HOD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hod_id")
    private Long hodId;

    private String name;
    private String emailId;
    private String photo;
}
