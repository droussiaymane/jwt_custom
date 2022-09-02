package com.project.demo.controller;

import com.project.demo.dao.Student;
import com.project.demo.dao.User;
import com.project.demo.message.ResponseMessage;
import com.project.demo.requests.UserRegisterRequest;
import com.project.demo.responses.UsernameResponse;
import com.project.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseMessage> createStudent(@RequestBody UserRegisterRequest user) {
        String message ="";
        try{
            User userCreated = userService.createUser(user);
            message="User is created successfully";
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(message));
        }
        catch (Exception e){
             message = "Error ! Try again ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }


    }
    @GetMapping("/username/{id}")
    public ResponseEntity getUsername(@PathVariable int id) {
        String message ="";
        try{
            String username = userService.getUsername(id);
            message="Done";
            UsernameResponse usernameResponse=new UsernameResponse();
            usernameResponse.setUsername(username);
            return ResponseEntity.status(HttpStatus.CREATED).body(usernameResponse);
        }
        catch (Exception e){
            message = "Error ! Try again ";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }


    }

}
