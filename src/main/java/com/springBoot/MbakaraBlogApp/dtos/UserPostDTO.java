package com.springBoot.MbakaraBlogApp.dtos;

import lombok.Data;

@Data
public class UserPostDTO {
    private Long id;
    private String title;
    private String description;
    private String content;

}
