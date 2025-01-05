package com.spdev.agileboard_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spdev.agileboard_backend.DTO.IssueDTO;
import com.spdev.agileboard_backend.Entity.IssuerequestEntity;
import com.spdev.agileboard_backend.modals.Issue;
import com.spdev.agileboard_backend.modals.User;
import com.spdev.agileboard_backend.response.MessageResponse;
import com.spdev.agileboard_backend.services.IssueService;
import com.spdev.agileboard_backend.services.UserService;

import java.util.List;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;





@RestController
@RequestMapping("/api/issue")
public class IssueController {
   
    @Autowired
     private IssueService issueService;

    @Autowired
     private UserService userService;
     

    @GetMapping("/{issueid}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long issueid) throws Exception{
        try {
            Issue issue = issueService.getIssueById(issueid);
            if(issue == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(issue);
        } catch (Exception e) {
            throw new InternalError(e.getMessage());
        }
       
    }
    

    @GetMapping("/{projectid}")
    public ResponseEntity<List<Issue>> getIssueByProjectId(@PathVariable Long projectId) throws Exception{
       try {
          List<Issue> issues = issueService.getIssueByProjectId(projectId);
          if(issues.isEmpty()){
             return ResponseEntity.notFound().build();
          }
          return ResponseEntity.ok().body(issues);
       } catch (Exception e) {
         throw new InternalError(e.getMessage());
       }
    }


    @PostMapping("/createIssue")
    public ResponseEntity<IssueDTO> createNewIssue(@RequestBody IssuerequestEntity issuerequest, @RequestHeader("Authorization") String jwt) throws Exception{
        try {
            User tokenUser = userService.findUserByjwt(jwt);
            Issue issue = issueService.createIssue(issuerequest, tokenUser);
            IssueDTO issueDTO = new IssueDTO(issue);
            return ResponseEntity.ok().body(issueDTO);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    @DeleteMapping("/{issueid}")

    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueid, @RequestHeader("Authorization") String jwt) throws Exception{
        try {
            User user = userService.findUserByjwt(jwt);
            issueService.deleteIssue(issueid, user.getId());
            MessageResponse response = new MessageResponse("Issue deleted successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
           throw new InternalError(e.getMessage());
        }
    }
     

    @PostMapping("/{issueid}/assignee/{userid}")
    public ResponseEntity<MessageResponse> assignUpsetoIssue(@PathVariable(required = true) Long issueid,
                                @PathVariable(required = true) Long userid) throws Exception{
        try {
            Issue issue = issueService.addUserToIssue(issueid, userid);
            if(issue == null) {
                return ResponseEntity.notFound().build();
            }
            MessageResponse response = new MessageResponse("Issue assigned successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
          throw new InternalException(e.getMessage());
        }
    }
    
   @PutMapping("updateStatus/{issueid}")
   public ResponseEntity<MessageResponse> putMethodName(@RequestBody String status, @PathVariable Long issueid) throws Exception{
      try {
        issueService.updateStatus(issueid, status);
        MessageResponse response = new MessageResponse("Issue status updated successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
      } catch (Exception e) {
         throw new InternalException(e.getMessage());
      }
   }
}
