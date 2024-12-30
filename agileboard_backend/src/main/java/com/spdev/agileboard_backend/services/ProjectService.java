package com.spdev.agileboard_backend.services;

import java.util.List;

import com.spdev.agileboard_backend.modals.Chat;
import com.spdev.agileboard_backend.modals.Projects;
import com.spdev.agileboard_backend.modals.User;

public interface ProjectService {
    
    Projects createProject(Projects project, User user) throws Exception;

    List<Projects> getProjectsbyteam(User user, String category, String tag) throws Exception;

    Projects getProjectById(Long projectid)throws Exception;

    void deleteProjectById(Long projectid, Long userid) throws Exception;


    Projects updateProjects(Projects updatedproject, Long userid) throws Exception;


    void addUserToProject(Long projectid, Long userid) throws Exception;

    void removeUserFromProject(Long projectid, Long userid) throws Exception;

    Chat getchatbyprojectid(Long projectid) throws Exception;

    List<Projects>searchProjects(String keyword , User user)throws Exception;
}
