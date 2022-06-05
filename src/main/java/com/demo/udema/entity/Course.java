package com.demo.udema.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "teacher_id") //FK
    private User users;

    @ManyToOne()
    @JoinColumn(name = "category_id") //FK
    private Category category;

    @Column(name = "data")
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "price")
    private double price;

    @Transient
    private double avgRating;

    @OneToOne(mappedBy = "course") // PK
    private CourseDetails courseDetails;
    //       -----------ManyToMany---users-orders-courses---------------
    @ManyToMany(mappedBy = "courses") // 2x PK
    private Set<User> user = new HashSet<>();
    // -----------------------------------------------------------------

    public Course() {
    }

    public Course(int id, User users, Category category, String timeStamp, String title, double price, double avgRating, CourseDetails courseDetails, Set<User> user) {
        this.id = id;
        this.users = users;
        this.category = category;
        this.timeStamp = timeStamp;
        this.title = title;
        this.price = price;
        this.avgRating = avgRating;
        this.courseDetails = courseDetails;
        this.user = user;
    }

    public Course(User users, Category category, String timeStamp, String title, double price, double avgRating, CourseDetails courseDetails, Set<User> user) {
        this.users = users;
        this.category = category;
        this.timeStamp = timeStamp;
        this.title = title;
        this.price = price;
        this.avgRating = avgRating;
        this.courseDetails = courseDetails;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public CourseDetails getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(CourseDetails courseDetails) {
        this.courseDetails = courseDetails;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Course{" +
                "title='" + title + '\'' +
                '}';
    }
}
