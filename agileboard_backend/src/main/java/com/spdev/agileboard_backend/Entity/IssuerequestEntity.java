package com.spdev.agileboard_backend.Entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuerequestEntity {
  
    private String title;

    private String description;

    private String status;

    private Long projectID;

    private String priority;

    private LocalDate dueDate;


}
