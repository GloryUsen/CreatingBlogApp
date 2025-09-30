package com.springBoot.MbakaraBlogApp.controller;

import com.springBoot.MbakaraBlogApp.dtos.UserPostDTO;
import com.springBoot.MbakaraBlogApp.entity.UsersPost;
import com.springBoot.MbakaraBlogApp.service.UserPostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class UserPostController {

    private UserPostService userPostService;

    public UserPostController(UserPostService userPostService) {
        this.userPostService = userPostService;
    }
    // creating Blog Post rest api
    @PostMapping
    public ResponseEntity<UserPostDTO> createPost(@RequestBody UserPostDTO userPostDTO){
        return new ResponseEntity<>(userPostService.createPost(userPostDTO), HttpStatus.CREATED);

    }

    // Get all Post Api
    @GetMapping
    public List<UserPostDTO> getAllPosts(@RequestParam(value = "pageNo", defaultValue = "0", required = false)
                                             int pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = "10", required = false)
                                         int pageSize )
    /** Adding pagination to this method parameter
 **/
 {
        return userPostService.getAllPosts();

    }

//    // Get Post by Id

    @GetMapping("/{id}")
    public ResponseEntity<UserPostDTO> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(userPostService.getPostById(id));
    }

    // Update post by id rest
    @PutMapping("{id}")
    public ResponseEntity<UserPostDTO> updateUserPost(@RequestBody UserPostDTO userPostDTO,
                                                      @PathVariable(name = "id") long id){
        UserPostDTO postResponse = userPostService.updateUserPost(userPostDTO, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // Deleting post rest API

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserPost(@PathVariable(name = "id") long id){
        userPostService.deleteUserPostById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);

    }
}
