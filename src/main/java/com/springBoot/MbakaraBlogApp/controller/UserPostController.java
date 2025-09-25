package com.springBoot.MbakaraBlogApp.controller;

import com.springBoot.MbakaraBlogApp.service.UserPostService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class UserPostController {

    private UserPostService userPostService;
}
