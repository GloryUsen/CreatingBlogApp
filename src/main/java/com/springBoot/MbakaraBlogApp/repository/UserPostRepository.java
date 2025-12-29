package com.springBoot.MbakaraBlogApp.repository;

import com.springBoot.MbakaraBlogApp.entity.UsersPost;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostRepository extends JpaRepository<UsersPost, Long> {
    // This is a standard naming convention to create a query method in spring data jpa.
    // So the findBy means to get an Entity or to Retrieve an Entity followed by the field name(category)
    List<UsersPost> findByCategoryId(Long categoryId);
}
