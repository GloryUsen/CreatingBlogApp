package com.springBoot.MbakaraBlogApp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springBoot.MbakaraBlogApp.dtos.JWTAuthResponse;
import com.springBoot.MbakaraBlogApp.dtos.LoginDTO;
import com.springBoot.MbakaraBlogApp.dtos.RegisterDTO;
import com.springBoot.MbakaraBlogApp.service.AuthService;


@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final DaoAuthenticationProvider authenticationProvider;

    private final AuthenticationManager authenticationManager;

    private AuthService authService;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, DaoAuthenticationProvider authenticationProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.authenticationProvider = authenticationProvider;
    }

    //Build Login REST API(Updating Login Endpoint to return JWT Token)

   @PostMapping(value = {"login", "/signing"})
   public ResponseEntity<JWTAuthResponse> loginUser(@RequestBody LoginDTO loginDTO){
       String token = authService.login(loginDTO);

       // Creating instance of JWTAuthResponse class.authenticationManager.authenticationProvider.
       JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
       jwtAuthResponse.setAccessToken(token);
       return ResponseEntity.ok(jwtAuthResponse);

   }

    //    @PostMapping(value = {"login", "/signing"})
    //    public ResponseEntity<Map<String, String>> loginUser (@RequestBody LoginDTO loginDTO){
    //        String token = authService.login(loginDTO);
    //        Map<String, String> response = new HashMap<>();
    //        response.put("token", token);
    //        response.put("message", "User login successful.!!");
    //        return ResponseEntity.ok(response);
    //    }
       


    // @PostMapping(value = {"login", "/signing"})
    // public ResponseEntity<String> loginUser(@RequestBody LoginDTO login){ // fix this by adding @RequestBody
    //     authService.login(login);
    //     return ResponseEntity.ok("User login successful.!!");
    // }



    // Building Register REST API
    @PostMapping(value = {"/register","signup"})
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO){
        String responseApi = authService.register(registerDTO);
        return new ResponseEntity<>(responseApi, HttpStatus.CREATED);
    }
}
