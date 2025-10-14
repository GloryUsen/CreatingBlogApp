package com.springBoot.MbakaraBlogApp.serviceImpl;

import com.springBoot.MbakaraBlogApp.dtos.UserPostDTO;
import com.springBoot.MbakaraBlogApp.dtos.UsersPostResponse;
import com.springBoot.MbakaraBlogApp.entity.UsersPost;
import com.springBoot.MbakaraBlogApp.exception.ResourceNotFoundException;
import com.springBoot.MbakaraBlogApp.repository.UserPostRepository;
import com.springBoot.MbakaraBlogApp.service.UserPostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPostServiceImpl implements UserPostService {

    private UserPostRepository userPostRepository;
    private ModelMapper mapper; // Creating a field

    public UserPostServiceImpl(UserPostRepository userPostRepository, ModelMapper mapper) {
        this.userPostRepository = userPostRepository;
        this.mapper = mapper;
    }

    @Override
    public UserPostDTO createPost(UserPostDTO userPostDTO) {

                 // Converting DTO into ENTITY

//        UsersPost post = new UsersPost();
//        post.setTitle(userPostDTO.getTitle());
//        post.setDescription(userPostDTO.getDescription());
//        post.setContent(userPostDTO.getContent());

        UsersPost usersPost = mapUsersPostToEntity(userPostDTO);
        UsersPost newPost = userPostRepository.save(usersPost);

        // Converting UserPostEntity(newPost) Into DTO

//        UserPostDTO postResponse = new UserPostDTO();
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setDescription(newPost.getDescription());
//        postResponse.setContent(newPost.getContent());
//        return postResponse;

        // The above-commented method is replaced with the mapToDTO method below

        UserPostDTO postResponse = mapUsersPostToDTO(newPost);
        return postResponse;

    }

    @Override
    //public List<UserPostDTO> getAllPosts(int pageNo, int pageSize) {
        public UsersPostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
        // So if sortDir is in ascending order, the sortBy obj should be crated in ascending order too. both should be created same.
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        /** Creating Instance of Pagination below */
        //Pageable pageable = PageRequest.of(pageNo, pageSize); this line of code is changed to the below line because of sorting.
        Pageable paging = PageRequest.of(pageNo, pageSize, sort);

        Page<UsersPost> posts = userPostRepository.findAll(paging);
        // getting content for pageable

        List<UsersPost> listOfPost = posts.getContent();

       /** List<UsersPost> posts = userPostRepository.findAll();
        return posts.stream().map(usersPost -> mapToDTO(usersPost).collect(Collectors.toList()));
        return posts.stream().map(this::mapToDTO)   // convert each entity into a DTO
                .collect(Collectors.toList());
        **/

        List<UserPostDTO> content = listOfPost.stream().map(usersPost -> mapUsersPostToDTO(usersPost)).collect(Collectors.toList());

        UsersPostResponse postResponse = new UsersPostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());


       // return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
        return postResponse;

    }

    @Override
    public UserPostDTO getPostById(long id) {
        UsersPost post = userPostRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Post", "id", id));
//here the method expects PostDto return type, so convert Post entity to Dto, call mapToDTO method.
        return mapUsersPostToDTO(post);
    }

    @Override
    public UserPostDTO updateUserPost(UserPostDTO postDTO, long id) {
        UsersPost updatePost = userPostRepository.findById(id).orElseThrow(() -> new
                ResourceNotFoundException("UpdatePost", "id", id));// if the post is not found by
        // the giving id, then it throws an exception

        updatePost.setTitle(postDTO.getTitle());
        updatePost.setDescription(postDTO.getDescription());
        updatePost.setContent(postDTO.getContent());
        // Set the updated values to post-Entity, set title

        UsersPost updatedPost = userPostRepository.save(updatePost); // saving the post into db.

        // Below is to return a post dto to the controller layer, so it's just to return by converting
        // the updated post into dto So call mapToDTO() method and pass in updated post

        return mapUsersPostToDTO(updatedPost);

    }

    @Override
    public void deleteUserPostById(long id) {
        UsersPost deletingPost = userPostRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("DeleteUsersPost", "id", id));// This line fetches code with id, if not exist, it throws exception.
        userPostRepository.delete(deletingPost);

    }


    //Converted Entity to DTO
//    private UserPostDTO mapUsersPostToDTO(UsersPost post){
//        UserPostDTO postDTO = new UserPostDTO();
//        postDTO.setId(post.getId());
//        postDTO.setTitle(post.getTitle());
//        postDTO.setDescription(post.getDescription());
//        postDTO.setContent(post.getContent());
//        return postDTO;
//    }

    /**
        The above-commented code method was for manually converting Entity to Dto.
        The below code is for using ModelMapper APi's method for that action, so mapUsersPostDto will
         be changed to ModelMapper
     */

    private UserPostDTO mapUsersPostToDTO(UsersPost usersPost){
        UserPostDTO postEntity = mapper.map(usersPost, UserPostDTO.class); // Connecting UsersPost to DTO.
        return postEntity;
    }



//    //Converting DTO to Entity
//    private UsersPost mapUsersPostToEntity(UserPostDTO dto){
//        UsersPost usersPost = new UsersPost();
//        usersPost.setTitle(dto.getTitle());
//        usersPost.setDescription(dto.getDescription());
//        usersPost.setContent(dto.getContent());
//        return usersPost;
//    }

    /**
       Same method here in changing/Converting DTO to Entity
     */

    private UsersPost mapUsersPostToEntity(UserPostDTO dto){
        UsersPost postDto = mapper.map(dto, UsersPost.class);
        return postDto;
    }
}
