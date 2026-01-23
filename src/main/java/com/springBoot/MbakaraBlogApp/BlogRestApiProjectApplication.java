package com.springBoot.MbakaraBlogApp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.springBoot.MbakaraBlogApp.entity.RolePlayed;
import com.springBoot.MbakaraBlogApp.repository.RolePlayedRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import jakarta.annotation.PostConstruct;
import io.swagger.v3.oas.annotations.ExternalDocumentation;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.springBoot.MbakaraBlogApp.repository")
@EntityScan(basePackages = "com.springBoot.MbakaraBlogApp.entity")
@OpenAPIDefinition(
    info = @Info(
        title = "MBAKARA BLOG APP REST APIs",
        description = "Spring boot App REST APIs Documentation",
        version = "v1.0",
        contact = @Contact(
            name = "Mbakara",
            email = "ganwana89@gmail.com",
            url = "https//github.com/GloryUsen"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.github.com/GloryUsen/MbakaraBlogApp/blob/main/LICENSE"
        )
    ),
    externalDocs = @ExternalDocumentation(
        description = "Mbakara Blog App Documentation",
        url = "https://github.com/GloryUsen/CreatingBlogApp"
    )
)
public class BlogRestApiProjectApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogRestApiProjectApplication.class, args);
    }

    @Autowired
    private RolePlayedRepository rolePlayedRepository;

    @Override
    public void run(String... args) {
        RolePlayed roleAdmin = new RolePlayed();
        roleAdmin.setName("ROLE_ADMIN");
        rolePlayedRepository.save(roleAdmin);

        RolePlayed roleUser = new RolePlayed();
        roleUser.setName("ROLE_USER");
        rolePlayedRepository.save(roleUser);
    }

    @PostConstruct
    public void checkEnv(){
            System.out.println("PGHOST = " + System.getenv("PGHOST"));
        System.out.println("PGPORT = " + System.getenv("PGPORT"));
        System.out.println("PGDATABASE = " + System.getenv("PGDATABASE"));
        System.out.println("PGUSER = " + System.getenv("PGUSER"));
        System.out.println("APP_JWT_SECRET = " + System.getenv("APP_JWT_SECRET"));
    }
    
}
