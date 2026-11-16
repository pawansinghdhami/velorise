package com.mobility.platform.authenticationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity {

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
