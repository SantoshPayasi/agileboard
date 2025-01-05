package com.spdev.agileboard_backend.Implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spdev.agileboard_backend.Entity.IssuerequestEntity;
import com.spdev.agileboard_backend.modals.Issue;
import com.spdev.agileboard_backend.modals.Projects;
import com.spdev.agileboard_backend.modals.User;
import com.spdev.agileboard_backend.repository.IssueRepository;
import com.spdev.agileboard_backend.services.IssueService;
import com.spdev.agileboard_backend.services.ProjectService;
import com.spdev.agileboard_backend.services.UserService;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public Issue getIssueById(Long id) throws Exception {
        try {
            Issue issue = issueRepository.findById(id).orElse(null);
            if (issue == null) {
                throw new Exception("Issue Not Found");
            }
            return issue;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Issue> getIssueByProjectId(Long projectId) throws Exception {
        try {
            List<Issue> issues = issueRepository.findByProjectID(projectId);
            return issues;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Issue createIssue(IssuerequestEntity issueRequest, User user) throws Exception {
        try {
            Projects project = projectService.getProjectById(issueRequest.getProjectID());

            Issue issue = new Issue();
            issue.setTitle(issueRequest.getTitle());
            issue.setDescription(issueRequest.getDescription());
            issue.setDueDate(issueRequest.getDueDate());
            issue.setProjectID(issueRequest.getProjectID());
            issue.setPriority(issueRequest.getPriority());
            issue.setStatus(issueRequest.getStatus());
            issue.setProject(project);
            return issueRepository.save(issue);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) throws Exception {
        try {
            issueRepository.deleteById(issueId);
        } catch (Exception e) {
           throw new Exception(e.getMessage());
        }
      
    }

    @Override
    public Issue addUserToIssue(Long issueId, Long userId) throws Exception {
        try {
            Issue issue = getIssueById(issueId);
            User user = userService.findUserByUserID(userId);
            issue.setAssignee(user);
            Issue updatedissue = issueRepository.save(issue);
            return updatedissue;
        } catch (Exception e) {
          throw new Exception(e.getMessage());
        }
    }

    @Override
    public Issue updateStatus(Long issueId, String status) throws Exception {
       try {
        Issue issue = getIssueById(issueId);
        issue.setStatus(status);
        return issueRepository.save(issue);
       } catch (Exception e) {
        throw new Exception(e.getMessage());
       }
    }

}
