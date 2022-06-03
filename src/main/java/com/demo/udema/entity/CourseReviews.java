package com.demo.udema.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "course_reviews")
public class CourseReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "user_id") //FK
    private User users;

    @ManyToOne()
    @JoinColumn(name = "course_details_id") //FK
    private CourseDetails courseDetails;

    @Column(name = "data")
    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

    @Column(name = "rating", columnDefinition = "double default 0")
    private Double rating;

    @Column(name = "review", nullable=true)
    private String review;

    public CourseReviews() {
    }

    public CourseReviews(int id, User users, CourseDetails courseDetails, String timeStamp, double rating, String review) {
        this.id = id;
        this.users = users;
        this.courseDetails = courseDetails;
        this.timeStamp = timeStamp;
        this.rating = rating;
        this.review = review;
    }

    public CourseReviews(User users, CourseDetails courseDetails, String timeStamp, double rating, String review) {
        this.users = users;
        this.courseDetails = courseDetails;
        this.timeStamp = timeStamp;
        this.rating = rating;
        this.review = review;
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

    public CourseDetails getCourseDetails() {
        return courseDetails;
    }

    public void setCourseDetails(CourseDetails courseDetails) {
        this.courseDetails = courseDetails;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
