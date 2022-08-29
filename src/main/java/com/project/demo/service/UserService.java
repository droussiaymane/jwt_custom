package com.project.demo.service;

import com.project.demo.dao.User;
import com.project.demo.requests.UserRegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    public User getUser(String email);
    public User createUser(UserRegisterRequest user);


}