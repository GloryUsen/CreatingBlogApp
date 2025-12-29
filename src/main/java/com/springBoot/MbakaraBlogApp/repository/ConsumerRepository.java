package com.springBoot.MbakaraBlogApp.repository;

import com.springBoot.MbakaraBlogApp.entity.Consumers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsumerRepository extends JpaRepository<Consumers, Long>{

    Optional<Consumers> findByConsumersEmail(String email);
   Optional<Consumers> findByConsumersUsernameOrConsumersEmail(String consumersUsername, String consumersEmail);
    Optional<Consumers> findByConsumersUsername(String consumersUsername);
    Boolean existsByConsumersUsername(String consumersUsername);
    Boolean existsByConsumersEmail(String consumersEmail);

}
