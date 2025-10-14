package com.springBoot.MbakaraBlogApp.service;

import com.springBoot.MbakaraBlogApp.dtos.UsersCommentDTO;

import java.util.List;

public interface UsersCommentService {

    UsersCommentDTO createComment(long usersPostId, UsersCommentDTO commentsObject);
    List<UsersCommentDTO> getAllCommentByPostId(long postId);

    UsersCommentDTO getCommentById(Long postId, Long usersCommentId);

    UsersCommentDTO updateComment(Long postId, long commentId, UsersCommentDTO commentRequest);

    void deleteUsersComment(Long postId, Long usersCommentId);


}
