package com.demo.udema.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "data")
    private Date date;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "role")
    private String role;

    @Column(name = "password")
    private String password;

    @Column(name = "isEnabled")
    private boolean isEnabled;

    // Mapping
    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Course> courses2;
    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
    private CourseReviews courseReviews;

    //-----------ManyToMany---users-orders-courses--------------------
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "orders",
    joinColumns = { @JoinColumn(name = "user_id") },           //PK FK
    inverseJoinColumns = { @JoinColumn(name = "course_id") } ) //FK

    Set<Course> courses = new HashSet<>();
    //----------------------------------------------------------------

    @Transient
    private String passwordConfirm;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(int id, Date date, String name, String surname, String username, String role, String password, boolean isEnabled, List<Course> courses2, CourseReviews courseReviews, Set<Course> courses) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.role = role;
        this.password = password;
        this.isEnabled = isEnabled;
        this.courses2 = courses2;
        this.courseReviews = courseReviews;
        this.courses = courses;
    }

    public User(Date date, String name, String surname, String username, String role, String password, boolean isEnabled) {
        this.date = date;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.role = role;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public List<Course> getCourses2() {
        return courses2;
    }

    public void setCourses2(List<Course> courses2) {
        this.courses2 = courses2;
    }

    public CourseReviews getCourseReviews() {
        return courseReviews;
    }

    public void setCourseReviews(CourseReviews courseReviews) {
        this.courseReviews = courseReviews;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
