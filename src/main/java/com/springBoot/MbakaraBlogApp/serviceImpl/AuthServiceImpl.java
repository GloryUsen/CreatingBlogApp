package com.springBoot.MbakaraBlogApp.serviceImpl;

import com.springBoot.MbakaraBlogApp.dtos.LoginDTO;
import com.springBoot.MbakaraBlogApp.dtos.RegisterDTO;
import com.springBoot.MbakaraBlogApp.entity.Consumers;
import com.springBoot.MbakaraBlogApp.entity.RolePlayed;
import com.springBoot.MbakaraBlogApp.exception.BlogCommentPostException;
import com.springBoot.MbakaraBlogApp.repository.ConsumerRepository;
import com.springBoot.MbakaraBlogApp.repository.RolePlayedRepository;
import com.springBoot.MbakaraBlogApp.security.JwtTokenProvider;
import com.springBoot.MbakaraBlogApp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private ConsumerRepository consumerRepository;
    private RolePlayedRepository rolePlayedRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           ConsumerRepository consumerRepository,
                           RolePlayedRepository rolePlayedRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.consumerRepository = consumerRepository;
        this.rolePlayedRepository = rolePlayedRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public String login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDTO.getUsernameOrEmail(),
                loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    

    @Override
    public String register(RegisterDTO registerDTO) {

        // Add a method that checks if a username exists in the database
        if (consumerRepository.existsByConsumersUsername(registerDTO.getUsername())){
            throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Username already exists!.");
        }


        // Add a method that checks if an email exist in the database
        if (consumerRepository.existsByConsumersEmail(registerDTO.getEmail())){
            throw new BlogCommentPostException(HttpStatus.BAD_REQUEST, "Email already exists!.");
        }

        Consumers consumers = new Consumers();
        consumers.setConsumersName(registerDTO.getName());
        consumers.setConsumersUsername(registerDTO.getUsername());
        consumers.setConsumersEmail(registerDTO.getEmail());
        consumers.setConsumersPassword(passwordEncoder.encode(registerDTO.getPassword()));
        
        Set<RolePlayed> roles = new HashSet<>();
        RolePlayed userRoles = rolePlayedRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new RuntimeException("Default role not found in database"));
        roles.add(userRoles);
        consumers.setRoles(roles);


        consumerRepository.save(consumers);
        return "Consumer Registered Successfully!.";
    }
}
