package com.spdev.agileboard_backend.controllers;

import java.util.List;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spdev.agileboard_backend.modals.Comment;
import com.spdev.agileboard_backend.services.CommentService;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api/comment")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    @PostMapping("/createnew")
     public ResponseEntity<?> createnewComment(@RequestBody(required = true) String content, @RequestBody(required = true) Long userid, @RequestBody(required = true) Long issueid) throws Exception{
        try {
            Comment newComment = commentService.creatnewcomment(content, issueid, userid);
            if(newComment == null){
                return new ResponseEntity<>("Unable to create comment", HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(newComment, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
     }

     @GetMapping("/{commentid}")
     public ResponseEntity<Comment> getCommentByIssueId(@PathVariable(required = true) Long commentid) throws Exception{
        try {
            Comment comment = commentService.getCommentById(commentid);
            if(comment == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalException(e.getMessage());
        }
     }

     @GetMapping("/issue/{issueid}")
     public ResponseEntity<List<Comment>> getCOmmentsByIssueId(@PathVariable(required =  true) Long issueid) throws Exception {
         try {
            List<Comment> comments = commentService.getcommentsbyIssueId(issueid);
            if(comments.isEmpty()){ 
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }   
            return new ResponseEntity<>(comments, HttpStatus.OK);
         } catch (Exception e) {
            throw new InternalException(e.getMessage());
         }
     }
     
}
