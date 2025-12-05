package com.springBoot.MbakaraBlogApp.security;

import com.springBoot.MbakaraBlogApp.repository.CustomersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private CustomersRepository customersRepository;

    public CustomUserDetailsService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        customersRepository.findByConsumersUsernameOrConsumersEmail(usernameOrEmail, usernameOrEmail) // the parameter is passed twice because it's userOr email to find.
                .orElseThrow(() -> new UsernameNotFoundException("User  not found with this username or email:"+ usernameOrEmail));
        
        return null;
    }
}
