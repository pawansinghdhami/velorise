package com.mobility.platform.authenticationservice.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_user")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone", nullable = true)
    private String phone;
    @Column(name = "role", nullable = false)
    private String role;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "created_dtm", nullable = false)
    @Transient
    private LocalDateTime createdDtm;
    @Column(name = "updated_dtm", nullable = false)
    @Transient
    private LocalDateTime updatedDtm;
    @Column(name = "created_by", nullable = true)
    private String createdBy;
    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
}
