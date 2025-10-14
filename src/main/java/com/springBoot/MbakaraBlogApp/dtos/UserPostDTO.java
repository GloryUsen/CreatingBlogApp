package com.springBoot.MbakaraBlogApp.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UserPostDTO {
    private Long id;

    // Title shouldn't be empty or Null
    // Title should have at least 2 characters
    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 2, message = "UsersPost should have at least 2 characters")
    private String title;
    
    // Post-description should Not be Null, or Empty
    // post-description should have at least 10 characters.
    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 2, message = "UserPost description should have at least 10 characters")
    private String description;

    // UsrPost content should not be Null or Empty
    @NotEmpty(message = "Content cannot be empty")
    private String content;
    private Set<UsersCommentDTO> comments;

}
