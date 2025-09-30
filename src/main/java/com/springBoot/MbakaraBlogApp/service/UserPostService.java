package com.springBoot.MbakaraBlogApp.service;

import com.springBoot.MbakaraBlogApp.dtos.UserPostDTO;

import java.util.List;

public interface UserPostService {
    UserPostDTO createPost(UserPostDTO userPostDTO);


    List<UserPostDTO> getAllPosts(int pageNo, int pageSize);

    UserPostDTO getPostById(long id);

    UserPostDTO updateUserPost(UserPostDTO post, long id);

    void deleteUserPostById(long id);



}
