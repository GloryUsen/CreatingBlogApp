package com.springBoot.MbakaraBlogApp.service;

import com.springBoot.MbakaraBlogApp.dtos.UserPostDTO;
import com.springBoot.MbakaraBlogApp.dtos.UsersPostResponse;

import java.util.List;

public interface UserPostService {
    UserPostDTO createPost(UserPostDTO userPostDTO);


    //List<UserPostDTO> getAllPosts(int pageNo, int pageSize);
    UsersPostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    UserPostDTO getPostById(long id);

    UserPostDTO updateUserPost(UserPostDTO post, long id);

    void deleteUserPostById(long id);
    List<UserPostDTO> getUsersPostByCategory(Long categoryId);



}
