package com.spdev.agileboard_backend.services;

import java.util.List;

import com.spdev.agileboard_backend.modals.Comment;
import com.spdev.agileboard_backend.modals.User;

public interface CommentService {

    Comment creatnewcomment(Comment comment);

    Comment getCommentById(Long commentId);

    List<Comment> getCommentByUserId(User user);

    Comment updateComment(Comment comment, Long commentId);
} 
