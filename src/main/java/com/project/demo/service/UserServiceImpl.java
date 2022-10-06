package com.project.demo.service;

import com.project.demo.dao.User;
import com.project.demo.dao.UserEntityDetails;
import com.project.demo.repository.UserRepository;
import com.project.demo.requests.UserRegisterRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService{



    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;




    @Override
    public void updateFailedAttempts(int failAttempts, String email){
        User user=userRepository.findByMail(email);
        user.setFailedAttempt(failAttempts);
        userRepository.save(user);

    }
    @Override
    public void increaseFailedAttempts(User user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        updateFailedAttempts(newFailAttempts, user.getMail());
    }

    @Override
    public void resetFailedAttempts(String email) {
        updateFailedAttempts(0, email);
    }

    @Override
    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepository.save(user);
    }

    @Override
    public boolean unlockWhenTimeExpired(User user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setAccountNonLocked(true);
            user.setLockTime(null);
            user.setFailedAttempt(0);
            userRepository.save(user);
            return true;
        }

        return false;
    }



    @Override
    public User getUser(String email) throws UsernameNotFoundException{
        User user = userRepository.findByMail(email);
        if(user==null){
            throw new UsernameNotFoundException("Sorry, User Not exist");
        }
        return user;
    }


    @Override
    public User createUser(UserRegisterRequest user) {
        User myuser = userRepository.findByMail(user.getMail());
        if(myuser!=null){
            throw new RuntimeException("User exist already");
        }

        ModelMapper modelMapper = new ModelMapper();
        User userCreated = modelMapper.map(user, User.class);
        userCreated.setPassword(passwordEncoder.encode(user.getPassword()));
        userCreated.setAccountNonLocked(true);
        User userRegistred=userRepository.save(userCreated);
        // check the type of the user
        return userRegistred;
    }

    @Override
    public String getUsername(int id) {
        User user = userRepository.findById(id).get();
        if(user==null){
            throw new RuntimeException("No user");
        }
        return user.getUsername();
    }
}
