package com.spdev.agileboard_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.spdev.agileboard_backend.modals.Comment;


import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
   
    List<Comment> findCommentsbyIssueId(Long issueid);

    // List<Comment> findByUser(User user);
}
 