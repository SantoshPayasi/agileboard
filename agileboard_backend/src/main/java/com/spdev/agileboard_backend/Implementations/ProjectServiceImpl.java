package com.spdev.agileboard_backend.Implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spdev.agileboard_backend.modals.Chat;
import com.spdev.agileboard_backend.modals.Projects;
import com.spdev.agileboard_backend.modals.User;
import com.spdev.agileboard_backend.repository.ProjectRepository;
import com.spdev.agileboard_backend.services.ChatService;
import com.spdev.agileboard_backend.services.ProjectService;
import com.spdev.agileboard_backend.services.UserService;


@Service
public class ProjectServiceImpl implements ProjectService {


    @Autowired
    private ProjectRepository projectRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;
    @Override
    public Projects createProject(Projects project, User user) throws Exception {
        try {
            new Projects();
            Projects createProject = Projects
            .builder()
            .owner(user)
            .name(project.getName())
            .description(project.getDescription())
            .category(project.getCategory())
            .tags(project.getTags())
            .build();

            createProject.getTeam().add(user);


            projectRepository.save(createProject);

            Chat chat = new Chat();

            chat.setProject(createProject);
           
            Chat ProjectChat = chatService.createChat(chat);

            createProject.setChat(ProjectChat);


            userService.updateUserProjectSize(user, 1);

            return createProject;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Projects> getProjectsbyteam(User user, String category, String tag) throws Exception {
        try {
            List<Projects> projects= projectRepository.findByTeamContainingOrOwner(user, user);
            if(category!=null){
                projects = projects.stream().filter(project->project.getCategory().equals(category)).collect(Collectors.toList());
            }
            if(tag!=null){
                projects = projects.stream().filter(project->project.getTags().contains(tag)).collect(Collectors.toList());
            }
            return projects;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Projects getProjectById(Long projectid) throws Exception {
       try {
        Optional<Projects> project =  projectRepository.findById(projectid) ;
        if(project.isEmpty()){
            throw new Exception("Project Not Found");
        }
        return project.get();
       } catch (Exception e) {
         throw new Exception(e.getMessage());
       }
    }

    @Override
    public void deleteProjectById(Long projectid, Long userid) throws Exception {
        try {
            // first get project by projectid
            Projects project = getProjectById(projectid);

            // check if the user is the owner of the project
            if(!project.getOwner().getId().equals(userid)){
                throw new Exception("You are not the owner of this project");
            }

            // delete project from databse 
            projectRepository.deleteById(projectid);

            // reduce the size of the project from user count 

            userService.updateUserProjectSize(project.getOwner(), -1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Projects updateProjects(Projects updatedproject, Long userid) throws Exception {
       try {
           Projects project = getProjectById(userid);
           project.setCategory(updatedproject.getCategory());
           project.setDescription(updatedproject.getDescription());
           project.setName(updatedproject.getName());
           project.setTags(updatedproject.getTags());
           projectRepository.save(project);
           return project;
       } catch (Exception e) {
           throw new Exception(e.getMessage());
       }
    }

    @Override
    public void addUserToProject(Long projectid, Long userid) throws Exception {
      try {
           Projects project = getProjectById(projectid);
           User user = userService.findUserByUserID(userid);
           if(!project.getTeam().contains(user)){
               project.getTeam().add(user);
               project.getChat().getUsers().add(user);
           }
           projectRepository.save(project);
      } catch (Exception e) {
          throw new Exception(e.getMessage());
      }
    }

    @Override
    public void removeUserFromProject(Long projectid, Long userid) throws Exception {
       try {
         Projects project = getProjectById(projectid);
         User currentUser = userService.findUserByUserID(userid);
         if(project.getTeam().contains(currentUser)){
            project.getChat().getUsers().remove(currentUser);
            project.getTeam().remove(currentUser);
         }
         projectRepository.save(project);
       } catch (Exception e) {
         throw new Exception(e.getMessage());
       }
    }

    @Override
    public Chat getchatbyprojectid(Long projectid) throws Exception {
       try {
        Projects project = getProjectById(projectid);
        return project.getChat();
       } catch (Exception e) {
         throw new Exception(e.getMessage());
       }
    }

    @Override
    public List<Projects> searchProjects(String keyword, User user) throws Exception {
        try {
            String partialName = "%"+ keyword + "%"; 
            return projectRepository.findByNameContainingAndTeamContains(partialName, user);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
       
    }

}
