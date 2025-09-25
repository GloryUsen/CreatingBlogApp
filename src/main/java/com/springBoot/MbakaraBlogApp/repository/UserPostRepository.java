package com.springBoot.MbakaraBlogApp.repository;

import com.springBoot.MbakaraBlogApp.entity.UsersPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostRepository extends JpaRepository<UsersPost, Long> {
}
