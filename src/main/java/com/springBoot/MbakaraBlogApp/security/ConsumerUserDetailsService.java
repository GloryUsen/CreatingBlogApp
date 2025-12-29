package com.springBoot.MbakaraBlogApp.security;

import com.springBoot.MbakaraBlogApp.entity.Consumers;
import com.springBoot.MbakaraBlogApp.repository.ConsumerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ConsumerUserDetailsService implements UserDetailsService {

    private ConsumerRepository customersRepository;

    public ConsumerUserDetailsService(ConsumerRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Consumers consumer = customersRepository.findByConsumersUsernameOrConsumersEmail(usernameOrEmail, usernameOrEmail) // the parameter is passed twice because it's userOr email to find.
                .orElseThrow(() -> new UsernameNotFoundException("User not found with this username or email:"+ usernameOrEmail));
        List<SimpleGrantedAuthority> authorities = consumer.getRoles() // this is a set
                .stream() // it has a set()
                .map((rolePlayed ->  // a map() to convert one obj into another obj. so a role obj is being passed into and new instance created.
                        new SimpleGrantedAuthority("ROLE_" + rolePlayed.getName())))// so here a role is converted into a simpleAuthority.
                .collect(Collectors.toList()); // here the collect method is used to collect a result.
        // Line 33 helps to convert a set of Role into a set of GrantedAuthorities because Spring security basically
        // expects the SimpleGrantedAuthority.

        /** Next line of code is to convert the consumerObj above into Spring security provided obj by
         Returning the instance of a ConsumerObj.
         *
         */


        return new org.springframework.security.core.userdetails.User(consumer.getConsumersEmail(),
                consumer.getConsumersPassword(),
                authorities);

        /** Basically, we  created ConsumerUserDetailsService class, that impls spring security provided UserDetailsService
         interface, and then within that we have injected ConsumersRepository using constructor-based dependency injection
         and implemented load user by Username method, and within the method we called findByUsernameOrEmail method to load
         the Consumer/User from the database, either by username or email.

            * Next, if a User /consumer with a given email or username does not exist in the database, then a UserNotFound thrown custom
         error is thrown.
            * Next, a set-up Roles is converted into a set granted authorities and then an instance of User class that is provided
         by spring security, and then we have password, email, password and authorities to this Consumer obj
         Well spring security will use this class to load the User object from the database.

         The next is to configure his ConsumerUserDetailsService in a spring configuration, so that spring security can
         use the class to load the Consumer object from the database.

         So instead of using in-Memory authentication, will use database authentication.
         */
    }
}
