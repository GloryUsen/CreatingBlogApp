package com.springBoot.MbakaraBlogApp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Getting JWT Token from http request
        String token = getTokenFromRequest(request);

        //Validating Token if it exists, so if token is valid and exists, treat the user as logged in, if not requst continues as anonymous.
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){


            // Get Username from Token by loading the user.
            String username = jwtTokenProvider.getUsername(token);
           // Load the User from the database
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);


            // Tell spring that this user is Authnticated, it belongs to a logged-in user
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        filterChain.doFilter(request, response); // Contiune the request, i am done checking, Let the next filter decide what will happen.

    }

    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7, bearerToken.length());
        }
       // return bearerToken;
       return null;
    }


    // What this class does and does not do;
    /*. 1. Checks if token exists
        2. Checks if token is valid
        3. Loads the user
        4. Marks the request as authenticated

        What it does not do;
        1. it does say Allow this endpoint”
        2. it does not say “Block this endpoint”
        3. it does not say This user has permission”
    Rather the decision happens later in securityCong2.
     * */ 
}
