package com.demo.udema.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vartotojai")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "vardas")
    private String name;

    @Column(name = "pavarde")
    private String surname;

    @Column(name = "prisijungimoVardas")
    private String username;

    @Column(name = "slaptazodis")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Course> courses;

    @Transient
    private String passwordConfirm;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
