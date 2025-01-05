package com.spdev.agileboard_backend.controllers;

import org.springframework.http.*;

import com.spdev.agileboard_backend.Entity.InvitationRequest;
import com.spdev.agileboard_backend.modals.*;
import com.spdev.agileboard_backend.services.*;
import org.springframework.web.bind.annotation.*;

import com.spdev.agileboard_backend.response.MessageResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @GetMapping
    public ResponseEntity<List<Projects>> getProjects(@RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user = userService.findUserByjwt(jwt);
            List<Projects> projects = projectService.getProjectsbyteam(user, category, tag);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Projects> getProjectbyId(@PathVariable Long projectId,
            @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            Projects projects = projectService.getProjectById(projectId);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/createProject")
    public ResponseEntity<Projects> postMethodName(
            @RequestBody(required = true) Projects entity,
            @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user = userService.findUserByjwt(jwt);
            Projects newProject = projectService.createProject(entity, user);
            return new ResponseEntity<>(newProject, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Projects> updateProject(
            @PathVariable Long projectId,
            @RequestBody Projects entity) throws Exception {
        try {
            Projects updatedProject = projectService.updateProjects(entity, projectId);
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(
        @PathVariable Long projectId,
        @RequestHeader("Authorization") String jwt
        ) throws Exception {
        try {
            User user = userService.findUserByjwt(jwt);
            projectService.deleteProjectById(projectId, user.getId());
            MessageResponse response = new MessageResponse("Project deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/{keyword}")
    public ResponseEntity<List<Projects>> searchprojects(
        @PathVariable String keyword,
        @RequestHeader("AUthorization") String jwt
        ) throws Exception {
        try  {
           User user =  userService.findUserByjwt(jwt);
           List<Projects> projects = projectService.searchProjects(keyword, user);
           return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    

    @GetMapping("/{ProjectId}/chat")
    public ResponseEntity<Chat> getChatByProjectId(@PathVariable(required = true) Long projectId) throws Exception {
        try {
         Chat chat = projectService.getchatbyprojectid(projectId);
         return new ResponseEntity<>(chat, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    

    @GetMapping("/invite")
    public ResponseEntity<MessageResponse> invitProject(
        @RequestParam InvitationRequest invitationRequest,
        @RequestHeader("AUthorization") String jwt
        ) throws Exception {
        try  {
          invitationService.SendInvitation(invitationRequest.getEmail(), invitationRequest.getProjectId());
          MessageResponse response = new MessageResponse("Invitation sent Successfully");

          return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    

    @PostMapping("/acceptInvitation")
    public ResponseEntity<MessageResponse> postMethodName(@RequestParam String token, @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user = userService.findUserByjwt(jwt);
            Invitation invitation = invitationService.AcceptInvitation(token, user.getId());
            if(invitation == null){
                throw new Exception("Invalid Invitation");
            }
            projectService.addUserToProject(invitation.getProjectId(), user.getId());
            MessageResponse response = new MessageResponse("Invitation accepted successfully Welcome to project");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    

}
