package com.spdev.agileboard_backend.services;

import java.util.List;
import java.util.Optional;

import com.spdev.agileboard_backend.Entity.IssuerequestEntity;
import com.spdev.agileboard_backend.modals.Issue;
import com.spdev.agileboard_backend.modals.User;

public interface IssueService {
   Optional<Issue> getIssueById(Long id) throws Exception;

   List<Issue> getIssueByProjectId(Long projectId) throws Exception;

   Issue createIssue(IssuerequestEntity issueRequest, User user) throws Exception;

   void deleteIssue(Long issueId, Long userId) throws Exception;

//    List<Issue> getIssueByAssigneeId(Long assigneeId) throws Exception;

//    List<Issue> searchIssues(String title, String status, String priority, Long assigneeId) throws Exception;
   
  Issue addUserToIssue(Long issueId, Long userId) throws Exception;

  Issue updateStatus(Long issueId, String status) throws Exception;
} 
