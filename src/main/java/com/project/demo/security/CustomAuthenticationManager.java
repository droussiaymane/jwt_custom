package com.project.demo.security;

import com.project.demo.SpringApplicationContext;
import com.project.demo.dao.User;
import com.project.demo.repository.UserRepository;
import com.project.demo.service.UserService;
import com.sun.mail.imap.Rights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

public class CustomAuthenticationManager implements AuthenticationManager {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
        PasswordEncoder encoder = (PasswordEncoder) SpringApplicationContext.getBean("passwordEncoder");
        User user = userService.getUser(email);

        if (user != null) {
            if (user.isAccountNonLocked()) {
                if (!encoder.matches(password, user.getPassword())) {
                    if (user.getFailedAttempt() < userService.MAX_FAILED_ATTEMPTS - 1) {
                        userService.increaseFailedAttempts(user);
                    } else {
                        userService.lock(user);
                        throw new LockedException("Your account has been locked due to 3 failed attempts."
                                + " It will be unlocked after 24 hours.");
                    }
                    throw new BadCredentialsException("Password incorrect");
                }
                else {
                    return new UsernamePasswordAuthenticationToken(email, password, Arrays.asList(new SimpleGrantedAuthority(user.getRole())));

                }

            }
            else {
                if (userService.unlockWhenTimeExpired(user)) {
                    throw new LockedException("Your account has been unlocked. Please try to login again.");
                }
                else{
                    throw new LockedException("Your account has been locked due to 3 failed attempts."
                            + " It will be unlocked after 24 hours.");
                }


            }
        }

        else{
            throw new BadCredentialsException("user not found exception");
        }

    }

}
