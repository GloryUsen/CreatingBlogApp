package com.springBoot.MbakaraBlogApp.repository;

import com.springBoot.MbakaraBlogApp.entity.UsersCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersCommentRepository extends JpaRepository<UsersCommentEntity, Long> {
    List<UsersCommentEntity> findByUsersPostId(long usersPostId);


}
