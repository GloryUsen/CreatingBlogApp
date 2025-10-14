package com.springBoot.MbakaraBlogApp.controller;

import com.springBoot.MbakaraBlogApp.dtos.UserPostDTO;
import com.springBoot.MbakaraBlogApp.dtos.UsersPostResponse;
import com.springBoot.MbakaraBlogApp.entity.UsersPost;
import com.springBoot.MbakaraBlogApp.service.UserPostService;
import com.springBoot.MbakaraBlogApp.utils.ProjectConstance;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')") // Only admin can access this createPost api
    @PostMapping
    public ResponseEntity<UserPostDTO> createPost(@Valid @RequestBody UserPostDTO userPostDTO){
        return new ResponseEntity<>(userPostService.createPost(userPostDTO), HttpStatus.CREATED);

    }

    // Get all Post Api
    @GetMapping
    public UsersPostResponse getAllPosts(
                                         @RequestParam(value = "pageNo", defaultValue = ProjectConstance.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                         @RequestParam(value = "pageSize", defaultValue = ProjectConstance.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                         @RequestParam(value = "sortBy", defaultValue = ProjectConstance.DEFAULT_SORT_BY, required = false) String sortBy,
                                         @RequestParam(value = "sortBy", defaultValue = ProjectConstance.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    /** Adding pagination to this method parameter
 **/
 {
        return userPostService.getAllPosts(pageNo, pageSize, sortBy, sortDir);

    }

//    // Get Post by Id

    @GetMapping("/{id}")
    public ResponseEntity<UserPostDTO> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(userPostService.getPostById(id));
    }

    // Update post by id rest
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<UserPostDTO> updateUserPost(@Valid @RequestBody UserPostDTO userPostDTO,
                                                      @PathVariable(name = "id") long id){
        UserPostDTO postResponse = userPostService.updateUserPost(userPostDTO, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // Deleting post rest API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserPost(@PathVariable(name = "id") long id){
        userPostService.deleteUserPostById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);

    }
}
