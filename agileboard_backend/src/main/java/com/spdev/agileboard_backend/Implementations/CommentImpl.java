package com.spdev.agileboard_backend.Implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spdev.agileboard_backend.modals.Comment;
import com.spdev.agileboard_backend.modals.User;
import com.spdev.agileboard_backend.services.CommentService;

@Service
public class CommentImpl implements CommentService {

    @Override
    public Comment creatnewcomment(Comment comment) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'creatnewcomment'");
    }

    @Override
    public Comment getCommentById(Long commentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommentById'");
    }

    @Override
    public List<Comment> getCommentByUserId(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCommentByUserId'");
    }

    @Override
    public Comment updateComment(Comment comment, Long commentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateComment'");
    }

}
