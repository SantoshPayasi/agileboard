package com.spdev.agileboard_backend.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.spdev.agileboard_backend.modals.Issue;
import com.spdev.agileboard_backend.modals.Projects;
import com.spdev.agileboard_backend.modals.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
   
     public IssueDTO(Issue issue){
        this.id = issue.getId();
        this.title = issue.getTitle();
        this.description = issue.getDescription();
        this.status = issue.getStatus();
        this.projectID = issue.getProjectID();
        this.priority = issue.getPriority();
        this.dueDate = issue.getDueDate();
        this.tags = issue.getTags();
        this.assignee = issue.getAssignee();
        this.project = issue.getProject();
     }

    private Long id;


    private String title;

    private String description;

    private String status;

    private Long projectID;

    private String priority;

    private LocalDate dueDate;

    private List<String> tags = new ArrayList<>();


    private User assignee;


    private Projects project;
    


}
