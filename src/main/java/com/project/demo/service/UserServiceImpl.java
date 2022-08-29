package com.project.demo.service;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.project.demo.dao.Student;
import com.project.demo.dao.Teacher;
import com.project.demo.dao.User;
import com.project.demo.dao.UserEntityDetails;
import com.project.demo.repository.StudentRepository;
import com.project.demo.repository.TeacherRepository;
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

@Service
public class UserServiceImpl implements UserService{



    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByMail(email);
        if(user==null){
            logger.error("user not found");
            throw new UsernameNotFoundException("Sorry, User Not exist");
        }

        return new UserEntityDetails(user);
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


        // check the type of the user
        if(user.getRole().equals("ROLE_TEACHER")){
            Teacher teacher = modelMapper.map(user, Teacher.class);
            teacherRepository.save(teacher);
        }
        else{
            userCreated.setRole("ROLE_STUDENT");
            Student student = modelMapper.map(user, Student.class);
            studentRepository.save(student);
        }
        User userRegistred=userRepository.save(userCreated);
        return userRegistred;
    }
}
