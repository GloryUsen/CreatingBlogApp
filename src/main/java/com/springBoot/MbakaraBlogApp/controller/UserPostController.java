package com.springBoot.MbakaraBlogApp.controller;

import com.springBoot.MbakaraBlogApp.dtos.UserPostDTO;
import com.springBoot.MbakaraBlogApp.dtos.UserPostDTOV2;
import com.springBoot.MbakaraBlogApp.dtos.UsersPostResponse;
import com.springBoot.MbakaraBlogApp.service.UserPostService;
import com.springBoot.MbakaraBlogApp.utils.ProjectConstance;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping()
@Tag(
    name = "CRUD REST APIs for user Post Resource"
)
public class UserPostController {

    private UserPostService userPostService;

    public UserPostController(UserPostService userPostService) {
        this.userPostService = userPostService;
    }
    // creating Blog Post rest api
    @SecurityRequirement(
        name = "Bearer Authentication"
    )

    @Operation(
        summary = " Create UsersPost REST API",
        description = "Create UsersPost REST API is used to save users post into database"
    )

    @ApiResponse(
        responseCode = "201",
        description = "Http Status 201 CREATED"
    )

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Only admin can access this createPost api
    @PostMapping("/api/v1/posts")
    public ResponseEntity<UserPostDTO> createPost(@Valid @RequestBody UserPostDTO userPostDTO){
        return new ResponseEntity<>(userPostService.createPost(userPostDTO), HttpStatus.CREATED);

    }

    // Get all Post Api
     @Operation(
        summary = "Get All UsersPost REST API",
        description = "Get All UsersPost REST API is used to fetech all the post from the database"
    )

    @ApiResponse(
        responseCode = "200",
        description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/api/v1/posts")
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

 @Operation(
        summary = "Get UsersPost By Id REST API",
        description = "Get UsersPost By Id REST API is used to get a single post from the database"
    )

    @ApiResponse(
        responseCode = "200",
        description = "Http Status 200 SUCCESS"
    )
// Versioning Using Path Variable 
    // @GetMapping("/api/v1/posts/{id}")
    // public ResponseEntity<UserPostDTO> getPostByIdV1(@PathVariable(name = "id") long id){
    //     return ResponseEntity.ok(userPostService.getPostById(id));
    // }


    // Versioning Using Query Parameter
    
    @GetMapping(value = "/api/posts/{id}", params = "version=1")
    public ResponseEntity<UserPostDTO> getPostByIdV1(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(userPostService.getPostById(id));

    }

    // Versioning Through Custom Headers by Adding Headers to the REST APIs without Tags

    @GetMapping(value = "/api/posts/{id}", produces = "application/vnd.Mbakara.app-V1+json")
    public ResponseEntity<UserPostDTO> getPostByIdV1Content(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(userPostService.getPostById(id));
        
    }

    // Versioning Through Content Negotiation or Accept Header Versioning Without Tags
        
    @GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=1")
    public ResponseEntity<UserPostDTO> getPostByIdV1Header(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(userPostService.getPostById(id));
        
    }




    //   @GetMapping("/api/v2/posts/{id}")
    // public ResponseEntity<UserPostDTOV2> getPostByIdV2(@PathVariable(name = "id") long id){
    //     UserPostDTO userPostDTO = userPostService.getPostById(id);
    //     UserPostDTOV2 userPostDTOV2 = new UserPostDTOV2();
    //     userPostDTOV2.setId(userPostDTO.getId());
    //     userPostDTOV2.setTitle(userPostDTO.getTitle());
    //     userPostDTOV2.setContent(userPostDTO.getContent());
    //     List<String> tags = new ArrayList<>();
    //     tags.add("Java");
    //     tags.add("Spring Boot");
    //     tags.add("API");
    //     userPostDTOV2.setTags(tags);
    //     return ResponseEntity.ok(userPostDTOV2);
    // }

    // Versioning using Query Parameters with Tags
    @GetMapping(value = "/api/posts/{id}", params = "version=2")

    public ResponseEntity<UserPostDTOV2> getPostByIdV2(@PathVariable(name = "id") long id){
    UserPostDTO userPostDTO = userPostService.getPostById(id);
    UserPostDTOV2 userPostDTOV2 = new UserPostDTOV2();
    userPostDTOV2.setId(userPostDTO.getId());
    userPostDTOV2.setTitle(userPostDTO.getTitle());
    userPostDTOV2.setContent(userPostDTO.getContent());
    List<String> tags = new ArrayList<>();
    tags.add("Spring Boot");
    tags.add("API");
    tags.add("Java");
    userPostDTOV2.setTags(tags);
    return ResponseEntity.ok(userPostDTOV2);
    }


    // Versioning Through Custom Headers by Adding Headers to the REST APIs with Tags.
       @GetMapping(value = "/api/posts/{id}",headers = "X-API-VERSION=2")

    public ResponseEntity<UserPostDTOV2> getPostByIdV2Header(@PathVariable(name = "id") long id){
    UserPostDTO userPostDTO = userPostService.getPostById(id);
    UserPostDTOV2 userPostDTOV2 = new UserPostDTOV2();
    userPostDTOV2.setId(userPostDTO.getId());
    userPostDTOV2.setTitle(userPostDTO.getTitle());
    userPostDTOV2.setContent(userPostDTO.getContent());
    List<String> tags = new ArrayList<>();
    tags.add("Spring Boot");
    tags.add("API");
    tags.add("Java");
    userPostDTOV2.setTags(tags);
    return ResponseEntity.ok(userPostDTOV2);
    }


    // Versioning Through Content Negotiation or Accept Header Versioning With Tags
        @GetMapping(value = "/api/posts/{id}",  produces = "application/vnd.Mbakara.app-V2+json")

    public ResponseEntity<UserPostDTOV2> getPostByIdV2Content(@PathVariable(name = "id") long id){
    UserPostDTO userPostDTO = userPostService.getPostById(id);
    UserPostDTOV2 userPostDTOV2 = new UserPostDTOV2();
    userPostDTOV2.setId(userPostDTO.getId());
    userPostDTOV2.setTitle(userPostDTO.getTitle());
    userPostDTOV2.setContent(userPostDTO.getContent());
    List<String> tags = new ArrayList<>();
    tags.add("Spring Boot");
    tags.add("API");
    tags.add("Java");
    userPostDTOV2.setTags(tags);
    return ResponseEntity.ok(userPostDTOV2);
    }




    // Update post by id rest
    @SecurityRequirement(
        name = "Bearer Authentication"
    )
    @Operation(
        summary = "Update UsersPost REST API",
        description = "Update UsersPost REST API is used update a particular post in a database"
    )

    @ApiResponse(
        responseCode = "200",
        description = "Http Status 200 SUCCESS"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<UserPostDTO> updateUserPost(@Valid @RequestBody UserPostDTO userPostDTO,
                                                      @PathVariable(name = "id") long id){
        UserPostDTO postResponse = userPostService.updateUserPost(userPostDTO, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // Deleting post rest API
    @SecurityRequirement(
        name = "Bearer Authentication"
    )

     @Operation(
        summary = "Delete UsersPost REST API",
        description = "Delete UsersPost REST API is used to delete a particular post from the database"
    )

    @ApiResponse(
        responseCode = "200",
        description = "Http Status 200 SUCCESS"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deleteUserPost(@PathVariable(name = "id") long id){
        userPostService.deleteUserPostById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);

    }
    // Build Post by Category REST API
    // http://localhost:1212:api/posts/category/3
    @GetMapping("/category/{id}")
     public ResponseEntity<List<UserPostDTO>> getUsersPostByCategory(@PathVariable("id") Long categoryId){
        List<UserPostDTO> postDTO = userPostService.getUsersPostByCategory(categoryId); // The List is the Local Variable or typeLIST as generic, then userPostDTO as type.
        return ResponseEntity.ok(postDTO);
    }
}

