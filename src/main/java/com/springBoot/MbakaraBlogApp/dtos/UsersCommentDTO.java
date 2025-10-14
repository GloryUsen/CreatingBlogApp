package com.springBoot.MbakaraBlogApp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsersCommentDTO {
    private long id;

    // Name should not be Null or Empty
    @NotEmpty(message = "Name should not be Null or Empty")
    private String usersName;

    // Email should not be Null or Empty
    // Email field should be validated
    @NotEmpty(message = "Email should not b null or empty")
    @Email(message = "Email must be a well-formed email address")
    private String usersEmail;

    // Comment Body should not be Null or Empty
    // Comment body must be minimum of 10 characters
    @NotEmpty(message = "Comment body should not be null or empty")
    @Size(min = 10, message = "Comment body must be minimum of 10 characters")
    private String messageBody;


}
