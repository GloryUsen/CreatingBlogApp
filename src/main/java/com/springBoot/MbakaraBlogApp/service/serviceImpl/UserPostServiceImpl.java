package com.springBoot.MbakaraBlogApp.service.serviceImpl;

import com.springBoot.MbakaraBlogApp.dtos.UserPostDTO;
import com.springBoot.MbakaraBlogApp.entity.UsersPost;
import com.springBoot.MbakaraBlogApp.repository.UserPostRepository;
import com.springBoot.MbakaraBlogApp.service.UserPostService;
import org.springframework.stereotype.Service;

@Service
public class UserPostServiceImpl implements UserPostService {

    private UserPostRepository userPostRepository;

    public UserPostServiceImpl(UserPostRepository userPostRepository) {
        this.userPostRepository = userPostRepository;
    }

    @Override
    public UserPostDTO createPost(UserPostDTO userPostDTO) {

                 // Converting DTO into ENTITY

        UsersPost post = new UsersPost();

//      post.setId(userPostDTO.getId());
        post.setTitle(userPostDTO.getTitle());
        post.setDescription(userPostDTO.getDescription());
        post.setContent(userPostDTO.getContent());

        UsersPost newPost = userPostRepository.save(post);

        // Converting UserPostEntity(newPost) Into DTO

        UserPostDTO postResponse = new UserPostDTO();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());
        return postResponse;
    }
}
