package com.springBoot.MbakaraBlogApp.controller;

import com.springBoot.MbakaraBlogApp.dtos.UsersCommentDTO;
import com.springBoot.MbakaraBlogApp.service.UsersCommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class UsersCommentController {

    private UsersCommentService commentService;

    public UsersCommentController(UsersCommentService commentService) {
        this.commentService = commentService;
    }



    @PostMapping("/usersPost/{postId}/comments")
    public ResponseEntity<UsersCommentDTO> createComment(@PathVariable(value = "postId") long id,
                                                         @Valid @RequestBody UsersCommentDTO usersCommentDTO){
        return new ResponseEntity<>(commentService.createComment(id, usersCommentDTO), HttpStatus.CREATED);
    }



    @GetMapping("/usersPost/{postId}/comments")
    public List<UsersCommentDTO> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId){
        return commentService.getAllCommentByPostId(postId);
    }



    @GetMapping("/usersPost/{postId}/comments/{id}")
    public ResponseEntity<UsersCommentDTO> getCommentById(@PathVariable(value = "postId") Long postId,
                                                          @PathVariable(value = "id") Long userCommentId){
        UsersCommentDTO commentDTO = commentService.getCommentById(postId, userCommentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);

    }

    @PutMapping("/usersPost/{postId}/comments/{id}")
    public ResponseEntity<UsersCommentDTO> updatingComment(@PathVariable(value = "postId") Long postId,
                                                           @PathVariable(value = "id") Long commentId,
                                                           @Valid @RequestBody UsersCommentDTO commentDTO){

        UsersCommentDTO updateComment = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<>(updateComment, HttpStatus.OK);

    }

    @DeleteMapping("/usersPost/{postId}/comments/{id}")
    public ResponseEntity<String> deleteUsersComment(@PathVariable(value = "postId") Long postId,
                                                     @PathVariable(value = "id") Long usersCommentId){
        commentService.deleteUsersComment(postId, usersCommentId);
        return new ResponseEntity<>("Comment deleted Successfully", HttpStatus.OK);

    }
}
