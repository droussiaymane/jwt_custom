package com.project.demo.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="name")
    String name;
    @Column(name="username")
    String username;
    @Column(name="mail")
    String mail;
    @Column(name="password")
    String password;
    @Column(name="role")
    String role;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "failed_attempt")
    private int failedAttempt=0;

    @Column(name = "lock_time")
    private Date lockTime;
}
