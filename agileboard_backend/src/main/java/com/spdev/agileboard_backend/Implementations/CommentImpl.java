package com.spdev.agileboard_backend.Implementations;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spdev.agileboard_backend.modals.Comment;
import com.spdev.agileboard_backend.modals.Issue;
import com.spdev.agileboard_backend.modals.User;
import com.spdev.agileboard_backend.repository.CommentRepository;
import com.spdev.agileboard_backend.services.CommentService;
import com.spdev.agileboard_backend.services.IssueService;
import com.spdev.agileboard_backend.services.UserService;

@Service
public class CommentImpl implements CommentService {

    @Autowired
     private CommentRepository commentRepository;

    @Autowired
     private UserService userService;

    @Autowired
     private IssueService issueService;

    @Override
    public Comment creatnewcomment(String content, Long issueId, Long userid) throws Exception {
        try {
             User user = userService.findUserByUserID(userid);
             if(user == null){
                throw new Exception("User does not exist");
             }
            Issue issue = issueService.getIssueById(issueId);
            if(issue == null){
                throw new Exception("Issue does not exist");
            }

            Comment comment = new Comment();
            comment.setComment(content);
            comment.setUser(user);  
            comment.setIssue(issue);

            Comment savedcomment = commentRepository.save(comment);
            return savedcomment;
        } catch (Exception e) {
           throw new InternalException(e.getMessage());
        }
             
    }

    @Override
    public Comment getCommentById(Long commentId) throws Exception{
       try {
          Optional<Comment> comment = commentRepository.findById(commentId);
          if(comment.isEmpty()){
            throw new Exception("Comment Not Found");
          }
          return comment.get();
       } catch (Exception e) {
         throw new InternalException(e.getMessage());
       }
    }


    @Override
    public List<Comment> getcommentsbyIssueId(Long issueId) throws Exception{
        try {
            List<Comment> comments = commentRepository.findCommentsbyIssueId(issueId);
            return comments;
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
    }

}
