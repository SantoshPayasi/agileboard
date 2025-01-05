package com.spdev.agileboard_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.spdev.agileboard_backend.modals.Comment;
import com.spdev.agileboard_backend.modals.Issue;
import com.spdev.agileboard_backend.modals.User;

import java.util.List;


public interface CommnetRepository extends JpaRepository<Comment, Long> {
   
    List<Comment> findByIssue(Issue issue);

    List<Comment> findByUser(User user);
}
 