package com.springBoot.MbakaraBlogApp.repository;

import com.springBoot.MbakaraBlogApp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
