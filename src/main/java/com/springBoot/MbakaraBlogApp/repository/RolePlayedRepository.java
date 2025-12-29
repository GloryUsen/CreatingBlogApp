package com.springBoot.MbakaraBlogApp.repository;

import com.springBoot.MbakaraBlogApp.entity.RolePlayed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolePlayedRepository extends JpaRepository<RolePlayed, Long>{
    Optional<RolePlayed> findByName (String name);
}
