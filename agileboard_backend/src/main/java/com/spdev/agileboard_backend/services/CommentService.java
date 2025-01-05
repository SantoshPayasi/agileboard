package com.spdev.agileboard_backend.services;

import java.util.List;

import com.spdev.agileboard_backend.modals.Comment;

public interface CommentService {

    Comment creatnewcomment(String content, Long issueId, Long userid) throws Exception;

    Comment getCommentById(Long commentId) throws Exception;

    List<Comment> getcommentsbyIssueId(Long issueid) throws Exception;

} 
