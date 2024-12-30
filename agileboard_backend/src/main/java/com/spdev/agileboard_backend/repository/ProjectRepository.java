package com.spdev.agileboard_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spdev.agileboard_backend.modals.Projects;
import com.spdev.agileboard_backend.modals.User;

public interface ProjectRepository extends JpaRepository<Projects, Long> {
   


    List<Projects> findByNameContainingAndTeamContains(String pratialName, User user);



    List<Projects> findByTeamContainingOrOwner(User user, User owner);
}
