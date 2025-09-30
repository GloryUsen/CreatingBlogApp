package com.springBoot.MbakaraBlogApp.service.serviceImpl;

import com.springBoot.MbakaraBlogApp.dtos.UserPostDTO;
import com.springBoot.MbakaraBlogApp.entity.UsersPost;
import com.springBoot.MbakaraBlogApp.exception.ResourceNotFoundException;
import com.springBoot.MbakaraBlogApp.repository.UserPostRepository;
import com.springBoot.MbakaraBlogApp.service.UserPostService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPostServiceImpl implements UserPostService {

    private UserPostRepository userPostRepository;

    public UserPostServiceImpl(UserPostRepository userPostRepository) {
        this.userPostRepository = userPostRepository;
    }

    @Override
    public UserPostDTO createPost(UserPostDTO userPostDTO) {

                 // Converting DTO into ENTITY

//        UsersPost post = new UsersPost();
//        post.setTitle(userPostDTO.getTitle());
//        post.setDescription(userPostDTO.getDescription());
//        post.setContent(userPostDTO.getContent());

        UsersPost usersPost = mapToEntity(userPostDTO);
        UsersPost newPost = userPostRepository.save(usersPost);

        // Converting UserPostEntity(newPost) Into DTO

//        UserPostDTO postResponse = new UserPostDTO();
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setDescription(newPost.getDescription());
//        postResponse.setContent(newPost.getContent());
//        return postResponse;

        // The above-commented method is replaced with the mapToDTO method below

        UserPostDTO postResponse = mapToDTO(newPost);
        return postResponse;

    }

    @Override
    public List<UserPostDTO> getAllPosts(int pageNo, int pageSize) {
        List<UsersPost> posts = userPostRepository.findAll();
        //return posts.stream().map(usersPost -> mapToDTO(usersPost).collect(Collectors.toList()));
       // return posts.stream().map(this::mapToDTO)   // convert each entity into a DTO
              //  .collect(Collectors.toList());
        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());

    }

    @Override
    public UserPostDTO getPostById(long id) {
        UsersPost post = userPostRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", id));
//here the method expects PostDto return type, so convert Post entity to Dto, call mapToDTO method.
        return mapToDTO(post);
    }

    @Override
    public UserPostDTO updateUserPost(UserPostDTO postDTO, long id) {
        UsersPost updatePost = userPostRepository.findById(id).orElseThrow(() -> new
                ResourceNotFoundException("UpdatePost", "id", id));// if the post is not find by
        // the giving id, then it throws an exception

        updatePost.setTitle(postDTO.getTitle());
        updatePost.setDescription(postDTO.getDescription());
        updatePost.setContent(postDTO.getContent());
        // Set the updated values to post-Entity, set title

        UsersPost updatedPost = userPostRepository.save(updatePost); // saving the post into db.

        // Below is to return a post dto to the controller layer, so it's just to return by converting
        // the updated post into dto So call mapToDTO() method and pass in updated post

        return mapToDTO(updatedPost);

    }

    @Override
    public void deleteUserPostById(long id) {
        UsersPost deletingPost = userPostRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("DeleteUsersPost", "id", id));// This line fetches code with id, if not exist, it throws exception.
        userPostRepository.delete(deletingPost);

    }


    //Converted Entity to DTO
    private UserPostDTO mapToDTO(UsersPost post){
        UserPostDTO postDTO = new UserPostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        postDTO.setContent(post.getContent());
        return postDTO;
    }

    //Converting DTO to Entity

    private UsersPost mapToEntity(UserPostDTO dto){
        UsersPost usersPost = new UsersPost();
        usersPost.setTitle(dto.getTitle());
        usersPost.setDescription(dto.getDescription());
        usersPost.setContent(dto.getContent());
        return usersPost;
    }
}
