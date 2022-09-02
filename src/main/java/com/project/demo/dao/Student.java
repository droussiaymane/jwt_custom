package com.project.demo.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="student")
@Data
public class Student {
    @Id
    int id;
    @Column(name="name")
    String name;
    @Column(name="username")
    String username;
    @Column(name="mail")
    String mail;
}
