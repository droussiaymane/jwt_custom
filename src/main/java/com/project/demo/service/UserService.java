package com.project.demo.service;

import com.project.demo.dao.User;
import com.project.demo.requests.UserRegisterRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

public interface UserService  {

    public static final int MAX_FAILED_ATTEMPTS = 3;

     static final long LOCK_TIME_DURATION = 1000*10; // 24 hours

    public User getUser(String email);
    public User createUser(UserRegisterRequest user);

    public String getUsername(int id);




    public void updateFailedAttempts(int failAttempts, String email);
    public void increaseFailedAttempts(User user) ;
    public void resetFailedAttempts(String email);

    public void lock(User user) ;

    public boolean unlockWhenTimeExpired(User user) ;

}