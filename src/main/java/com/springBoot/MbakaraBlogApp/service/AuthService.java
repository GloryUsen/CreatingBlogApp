package com.springBoot.MbakaraBlogApp.service;

import com.springBoot.MbakaraBlogApp.dtos.LoginDTO;
import com.springBoot.MbakaraBlogApp.dtos.RegisterDTO;

public interface AuthService {

    String login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);



}
